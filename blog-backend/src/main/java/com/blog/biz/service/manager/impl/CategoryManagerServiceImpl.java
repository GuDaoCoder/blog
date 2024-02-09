package com.blog.biz.service.manager.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.blog.biz.model.request.CategoryTreeRequest;
import com.blog.biz.model.response.CategoryTreeResponse;
import com.blog.common.constant.SymbolConstants;
import com.blog.common.util.StreamUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.biz.constant.BizConstant;
import com.blog.biz.convert.CategoryConverter;
import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.request.UpdateCategoryRequest;
import com.blog.biz.model.response.CreateCategoryResponse;
import com.blog.biz.service.crud.CategoryCrudService;
import com.blog.biz.service.crud.PostCrudService;
import com.blog.biz.service.manager.CategoryManagerService;
import com.blog.common.exception.BusinessException;
import com.blog.common.util.SnowflakeUtil;
import com.blog.common.util.TreeUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryManagerServiceImpl implements CategoryManagerService {

    private final CategoryCrudService categoryCrudService;

    private final PostCrudService postCrudService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public CreateCategoryResponse create(CreateCategoryRequest request) {
        // 校验名称重复
        CategoryEntity existsEntity = categoryCrudService.findByCategoryName(request.getCategoryName()).orElse(null);
        if (Objects.nonNull(existsEntity)) {
            throw new BusinessException("分类名称已存在");
        }

        CategoryEntity entity = CategoryConverter.INSTANCE.toEntity(request);

        // 手动设置主键，方便设置fullId
        entity.setCategoryId((Long) SnowflakeUtil.getId());

        if (Objects.nonNull(request.getParentCategoryId())) {
            // 上级分类
            CategoryEntity parentEntity = categoryCrudService.getOptById(request.getParentCategoryId()).orElseThrow(() -> new BusinessException("上级分类信息不存在"));
            entity.setParentCategoryId(parentEntity.getCategoryId());
            entity.setLevel(parentEntity.getLevel() + 1);
            entity.setFullId(parentEntity.getFullId() + SymbolConstants.CENTER_LINE + entity.getCategoryId());
        } else {
            entity.setParentCategoryId(BizConstant.ROOT_ID);
            entity.setLevel(BizConstant.FIRST_LEVEL);
            entity.setFullId(String.valueOf(entity.getCategoryId()));
        }

        // 查询同一层级下最新的分类
        CategoryEntity latestEntity = categoryCrudService.findLatest(entity.getParentCategoryId()).orElse(null);
        // 设置顺序号
        entity.setOrderNo(Objects.nonNull(latestEntity) ? latestEntity.getOrderNo() + 1 : 1);

        categoryCrudService.save(entity);
        return new CreateCategoryResponse(entity.getCategoryId());
    }

    @Override
    public List<CategoryTreeResponse> tree(CategoryTreeRequest request) {
        List<CategoryEntity> entities = categoryCrudService.findAllByCondition(request.getCategoryName(), request.getEnabled());
        if (CollectionUtils.isNotEmpty(entities)) {
            List<Long> categoryIds = new ArrayList<>();
            entities.forEach(o -> {
                List<Long> ids = Arrays.stream(o.getFullId().split(SymbolConstants.CENTER_LINE)).map(Long::valueOf).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(ids)) {
                    categoryIds.addAll(ids);
                }
            });
            categoryIds.removeAll(entities.stream().map(CategoryEntity::getCategoryId).toList());
            if (CollectionUtils.isNotEmpty(categoryIds)) {
                List<CategoryEntity> otherEntities = categoryCrudService.listByIds(categoryIds);
                if (CollectionUtils.isNotEmpty(otherEntities)) {
                    entities.addAll(otherEntities);
                }
            }
        }
        List<CategoryTreeResponse> data = entities.stream()
                .map(CategoryConverter.INSTANCE::toResponse)
                .sorted(Comparator.comparing(CategoryTreeResponse::getOrderNo))
                .collect(Collectors.toList());
        return TreeUtil.build(data, BizConstant.ROOT_ID, CategoryTreeResponse::getCategoryId, CategoryTreeResponse::getParentCategoryId);
    }

    @Override
    public void update(Long categoryId, UpdateCategoryRequest request) {
        CategoryEntity entity = categoryCrudService.getById(categoryId);
        if (!StringUtils.equals(request.getCategoryName(), entity.getCategoryName())) {
            // 校验名称重复
            CategoryEntity existsEntity = categoryCrudService.findByCategoryName(request.getCategoryName()).orElse(null);
            if (Objects.nonNull(existsEntity)) {
                throw new BusinessException("分类名称已存在");
            }
        }
        entity.setCategoryName(request.getCategoryName());
        categoryCrudService.updateById(entity);
    }

    @Override
    public void delete(Long categoryId) {
        categoryCrudService.getOneOrThrow(categoryId);

        if (postCrudService.categoryUsed(categoryId)) {
            throw new BusinessException("分类已经被使用，无法删除");
        }

        categoryCrudService.removeById(categoryId);

    }

}
