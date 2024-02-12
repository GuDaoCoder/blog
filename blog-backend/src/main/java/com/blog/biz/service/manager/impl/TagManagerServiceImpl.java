package com.blog.biz.service.manager.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.blog.biz.model.entity.custom.TagPostCountEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.convert.TagConverter;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.PageTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
import com.blog.biz.model.response.CreateTagResponse;
import com.blog.biz.model.response.PageTagResponse;
import com.blog.biz.service.crud.PostTagRelaCrudService;
import com.blog.biz.service.crud.TagCrudService;
import com.blog.biz.service.manager.TagManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.exception.BusinessException;
import com.blog.common.util.PageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public CreateTagResponse create(CreateTagRequest request) {
        TagEntity entity = TagConverter.INSTANCE.toEntity(request);
        // 校验名称是否重复
        TagEntity existsTagEntity = tagCrudService.findByTagName(request.getTagName()).orElse(null);
        if (Objects.nonNull(existsTagEntity)) {
            throw new BusinessException("标签名称不能重复");
        }
        tagCrudService.save(entity);
        return new CreateTagResponse(entity.getTagId());
    }

    @Override
    public void update(Long tagId, UpdateTagRequest request) {
        TagEntity entity = tagCrudService.getOneOrThrow(tagId);
        if (!StringUtils.equals(request.getTagName(), entity.getTagName())) {
            TagEntity existsTagEntity = tagCrudService.findByTagName(request.getTagName()).orElse(null);
            if (Objects.nonNull(existsTagEntity)) {
                throw new BusinessException("标签名称不能重复");
            }
        }
        entity.setTagName(request.getTagName())
                .setColor(request.getColor())
                .setEnable(request.getEnable());
        tagCrudService.updateById(entity);
    }

    @Override
    public PageResponse<PageTagResponse> page(PageTagRequest request) {
        IPage<TagEntity> page = tagCrudService.page(request.getTagName(), PageUtil.pageable(request));
        Map<Long, Long> tagPostCountMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            List<Long> tagIds = page.getRecords().stream().map(TagEntity::getTagId).collect(Collectors.toList());
            tagPostCountMap = postTagRelaCrudService.getTagPostCountEntity(tagIds)
                    .stream()
                    .collect(Collectors.toMap(TagPostCountEntity::getTagId, TagPostCountEntity::getPostCount));
        }
        Map<Long, Long> finalTagPostCountMap = tagPostCountMap;
        return PageUtil.toResult(page, entity -> {
            PageTagResponse pageTagResponse = TagConverter.INSTANCE.toResponse(entity);
            Long postCount = Optional.ofNullable(finalTagPostCountMap.get(entity.getTagId())).orElse(0L);
            pageTagResponse.setPostCount(postCount);
            return pageTagResponse;
        });
    }

    @Override
    public void delete(Long tagId) {
        tagCrudService.getOneOrThrow(tagId);
        if (postTagRelaCrudService.tagUsed(tagId)) {
            throw new BusinessException("标签已被使用，无法删除");
        }
        tagCrudService.removeById(tagId);
    }
}
