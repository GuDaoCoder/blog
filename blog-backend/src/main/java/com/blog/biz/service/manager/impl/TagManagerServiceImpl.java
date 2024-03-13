package com.blog.biz.service.manager.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.convert.TagConverter;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.entity.custom.TagPostCountEntity;
import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.SearchTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.service.crud.PostTagRelaCrudService;
import com.blog.biz.service.crud.TagCrudService;
import com.blog.biz.service.manager.TagManagerService;
import com.blog.common.base.response.SearchResponse;
import com.blog.common.exception.BusinessException;
import com.blog.common.util.PageUtil;
import com.blog.common.util.StreamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TagManagerServiceImpl implements TagManagerService {

    private final TagCrudService tagCrudService;

    private final PostTagRelaCrudService postTagRelaCrudService;

    @Override
    public SearchResponse<TagResponse> search(SearchTagRequest request) {
        IPage<TagEntity> page = tagCrudService.page(request.getTagName(), PageUtil.pageable(request));
        return PageUtil.result(page, toResponses(page.getRecords()));
    }

    @Override
    public TagResponse create(CreateTagRequest request) {
        TagEntity entity = TagConverter.INSTANCE.toEntity(request);
        // 校验名称是否重复
        tagCrudService.findByTagName(request.getTagName()).ifPresent(o -> {
            throw new BusinessException("标签名称[{}]已存在", request.getTagName());
        });
        tagCrudService.save(entity);
        return toResponse(entity);
    }

    @Override
    public TagResponse update(Long tagId, UpdateTagRequest request) {
        TagEntity existEntity = tagCrudService.getOneOrThrow(tagId);
        if (!StringUtils.equals(request.getTagName(), existEntity.getTagName())) {
            tagCrudService.findByTagName(request.getTagName()).ifPresent(o -> {
                throw new BusinessException("标签名称[{}]已存在", request.getTagName());
            });
        }
        TagEntity entity = TagConverter.INSTANCE.toEntity(request);
        entity.setTagId(tagId);
        tagCrudService.updateById(entity);
        return toResponse(tagCrudService.getById(tagId));
    }

    @Override
    public void delete(Long tagId) {
        tagCrudService.getOneOrThrow(tagId);
        if (postTagRelaCrudService.tagUsed(tagId)) {
            throw new BusinessException("标签已被使用，无法删除");
        }
        tagCrudService.removeById(tagId);
    }

    private TagResponse toResponse(TagEntity tagEntity) {
        return CollUtil.getFirst(toResponses(List.of(tagEntity)));
    }

    private List<TagResponse> toResponses(List<TagEntity> tagEntities) {
        if (CollectionUtils.isEmpty(tagEntities)) {
            return new ArrayList<>();
        }
        List<Long> tagIds = StreamUtil.mapField(tagEntities, TagEntity::getTagId);

        Map<Long, Long> tagPostCountMap = postTagRelaCrudService.getTagPostCountEntity(tagIds)
                .stream()
                .collect(Collectors.toMap(TagPostCountEntity::getTagId, TagPostCountEntity::getPostCount));

        return tagEntities
                .stream()
                .map(tagEntity -> {
                    TagResponse tagResponse = TagConverter.INSTANCE.toResponse(tagEntity);
                    tagResponse.setPostCount(tagPostCountMap.getOrDefault(tagEntity.getTagId(), 0L));
                    return tagResponse;
                }).toList();
    }
}
