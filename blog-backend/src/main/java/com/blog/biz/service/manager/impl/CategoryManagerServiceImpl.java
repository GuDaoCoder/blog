package com.blog.biz.service.manager.impl;

import cn.hutool.core.collection.CollUtil;
import com.blog.biz.constant.BizConstant;
import com.blog.biz.convert.CategoryConverter;
import com.blog.biz.enums.OverviewType;
import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.model.entity.custom.CategoryPostCountEntity;
import com.blog.biz.model.request.CategoryAdminTreeRequest;
import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.request.UpdateCategoryRequest;
import com.blog.biz.model.response.CategoryResponse;
import com.blog.biz.model.response.CategoryTreeResponse;
import com.blog.biz.service.crud.CategoryCrudService;
import com.blog.biz.service.crud.PostCrudService;
import com.blog.biz.service.manager.CategoryManagerService;
import com.blog.biz.service.manager.OverviewStatisticService;
import com.blog.common.constant.SymbolConstants;
import com.blog.common.exception.BusinessException;
import com.blog.common.util.TreeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryManagerServiceImpl implements CategoryManagerService, OverviewStatisticService {

	private final CategoryCrudService categoryCrudService;

	private final PostCrudService postCrudService;

	@Override
	public List<CategoryTreeResponse> tree(CategoryAdminTreeRequest request) {
		List<CategoryEntity> categoryEntities = categoryCrudService.findAllByCondition(request.getCategoryName());
		if (CollectionUtils.isNotEmpty(categoryEntities)) {
			Set<Long> categoryIds = new HashSet<>();
			categoryEntities.forEach(o -> {
				Set<Long> ids = Arrays.stream(o.getFullId().split(SymbolConstants.CENTER_LINE))
					.map(Long::valueOf)
					.collect(Collectors.toSet());
				if (CollectionUtils.isNotEmpty(ids)) {
					categoryIds.addAll(ids);
				}
			});
			categoryEntities.stream().map(CategoryEntity::getCategoryId).toList().forEach(categoryIds::remove);
			if (CollectionUtils.isNotEmpty(categoryIds)) {
				List<CategoryEntity> otherEntities = categoryCrudService.listByIds(categoryIds);
				if (CollectionUtils.isNotEmpty(otherEntities)) {
					categoryEntities.addAll(otherEntities);
				}
			}
		}
		List<CategoryTreeResponse> data = toResponses(categoryEntities).stream()
			.map(CategoryConverter.INSTANCE::toTreeResponse)
			.sorted(Comparator.comparing(CategoryResponse::getOrderNo))
			.collect(Collectors.toList());
		return TreeUtil.build(data, BizConstant.ROOT_ID, CategoryTreeResponse::getCategoryId,
				CategoryTreeResponse::getParentCategoryId);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public CategoryResponse create(CreateCategoryRequest request) {
		// 校验名称重复
		categoryCrudService.findByCategoryName(request.getCategoryName()).ifPresent(o -> {
			throw new BusinessException("分类名称[{}]已存在", request.getCategoryName());
		});

		CategoryEntity categoryEntity = CategoryConverter.INSTANCE.toEntity(request);
		if (Objects.nonNull(request.getParentCategoryId())
				&& !request.getParentCategoryId().equals(BizConstant.ROOT_ID)) {
			// 上级分类
			CategoryEntity parentCategoryEntity = categoryCrudService.getOptById(request.getParentCategoryId())
				.orElseThrow(() -> new BusinessException("上级分类信息不存在"));
			categoryEntity.setParentCategoryId(parentCategoryEntity.getCategoryId())
				.setLevel(parentCategoryEntity.getLevel() + 1)
				.setFullId(
						parentCategoryEntity.getFullId() + SymbolConstants.CENTER_LINE + request.getParentCategoryId());
		}
		else {
			categoryEntity.setParentCategoryId(BizConstant.ROOT_ID)
				.setLevel(BizConstant.FIRST_LEVEL)
				.setFullId(String.valueOf(BizConstant.ROOT_ID));
		}

		// 查询同一层级下最新的分类
		CategoryEntity latestEntity = categoryCrudService.findLatest(categoryEntity.getParentCategoryId()).orElse(null);
		// 设置顺序号
		categoryEntity.setOrderNo(Objects.nonNull(latestEntity) ? latestEntity.getOrderNo() + 1 : 1);

		categoryCrudService.save(categoryEntity);
		return toResponse(categoryEntity);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public CategoryResponse update(Long categoryId, UpdateCategoryRequest request) {
		CategoryEntity existsCategoryEntity = categoryCrudService.getOneOrThrow(categoryId);
		if (!StringUtils.equals(request.getCategoryName(), existsCategoryEntity.getCategoryName())) {
			categoryCrudService.findByCategoryName(request.getCategoryName()).ifPresent(o -> {
				throw new BusinessException("分类名称[{}]已存在", request.getCategoryName());
			});
		}
		// 修改了所属上级分类
		if (!Objects.equals(request.getParentCategoryId(), BizConstant.ROOT_ID)
				&& !Objects.equals(request.getParentCategoryId(), existsCategoryEntity.getParentCategoryId())) {
			categoryCrudService.getOptById(request.getParentCategoryId())
				.orElseThrow(() -> new BusinessException("上级分类不存在"));
		}
		CategoryEntity categoryEntity = CategoryConverter.INSTANCE.toEntity(request);
		categoryEntity.setCategoryId(categoryId);
		categoryCrudService.updateById(categoryEntity);
		return toResponse(categoryCrudService.getById(categoryId));
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public void delete(Long categoryId) {
		categoryCrudService.getOneOrThrow(categoryId);

		if (postCrudService.categoryUsed(categoryId)) {
			throw new BusinessException("分类已经被使用，无法删除");
		}

		categoryCrudService.removeById(categoryId);
	}

	@Override
	public OverviewType overviewType() {
		return OverviewType.CATEGORY;
	}

	@Override
	public Long overviewCount() {
		return categoryCrudService.count();
	}

	private CategoryResponse toResponse(CategoryEntity categoryEntity) {
		if (categoryEntity == null) {
			return null;
		}
		return CollUtil.getFirst(toResponses(List.of(categoryEntity)));
	}

	private List<CategoryResponse> toResponses(List<CategoryEntity> categoryEntities) {
		if (CollectionUtils.isEmpty(categoryEntities)) {
			return new ArrayList<>();
		}
		Set<Long> categoryIds = categoryEntities.stream()
			.map(CategoryEntity::getCategoryId)
			.collect(Collectors.toSet());

		Set<Long> parentCategoryIds = categoryEntities.stream()
			.map(CategoryEntity::getParentCategoryId)
			.filter(parentCategoryId -> !Objects.equals(parentCategoryId, BizConstant.ROOT_ID))
			.collect(Collectors.toSet());

		Map<Long, String> categoryNameMap = categoryCrudService.listByIds(parentCategoryIds)
			.stream()
			.collect(Collectors.toMap(CategoryEntity::getCategoryId, CategoryEntity::getCategoryName));

		Map<Long, Long> categoryPostCountMap = postCrudService.getCategoryPostCountEntity(categoryIds)
			.stream()
			.collect(Collectors.toMap(CategoryPostCountEntity::getCategoryId, CategoryPostCountEntity::getPostCount));

		return categoryEntities.stream().map(entity -> {
			CategoryResponse categoryResponse = CategoryConverter.INSTANCE.toResponse(entity);
			categoryResponse.setPostCount(categoryPostCountMap.getOrDefault(entity.getCategoryId(), 0L))
				.setParentCategoryName(categoryNameMap.getOrDefault(entity.getParentCategoryId(), null));
			return categoryResponse;
		}).toList();
	}

}
