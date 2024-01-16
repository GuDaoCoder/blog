package com.blog.biz.service.manager.impl;

import java.util.Objects;

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
import com.blog.biz.model.response.TagResponse;
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
        TagEntity latestTagEntity = tagCrudService.findLatest().orElse(null);
        if (Objects.nonNull(latestTagEntity)) {
            // 校验名称是否重复
            TagEntity existsTagEntity = tagCrudService.findByTagName(request.getTagName()).orElse(null);
            if (Objects.nonNull(existsTagEntity)) {
                throw new BusinessException("标签名称不能重复");
            }
            entity.setOrderNo(latestTagEntity.getOrderNo() + 1);
        } else {
            entity.setOrderNo(1);
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
        entity.setTagName(request.getTagName());
        tagCrudService.updateById(entity);
    }

    @Override
    public PageResponse<TagResponse> page(PageTagRequest request) {
        IPage<TagEntity> page = tagCrudService.page(request.getTagName(), PageUtil.pageable(request));
        return PageUtil.toResult(page, TagConverter.INSTANCE::toResponse);
    }

    @Override
    public void delete(Long tagId) {
        tagCrudService.getOneOrThrow(tagId);

        if (postTagRelaCrudService.tagUsed(tagId)){
            throw new BusinessException("标签已被使用，无法删除");
        }

        tagCrudService.removeById(tagId);

    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void moveUp(Long tagId) {
        TagEntity entity = tagCrudService.getOneOrThrow(tagId);
        Integer currentOrderNo = entity.getOrderNo();
        tagCrudService.findPrevious(currentOrderNo).ifPresent(previousEntity -> {
            entity.setOrderNo(previousEntity.getOrderNo());
            tagCrudService.updateById(entity);

            previousEntity.setOrderNo(currentOrderNo);
            tagCrudService.updateById(previousEntity);
        });
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void moveDown(Long tagId) {
        TagEntity entity = tagCrudService.getOneOrThrow(tagId);
        Integer currentOrderNo = entity.getOrderNo();
        tagCrudService.findLatter(currentOrderNo).ifPresent(latterEntity -> {
            entity.setOrderNo(latterEntity.getOrderNo());
            tagCrudService.updateById(entity);

            latterEntity.setOrderNo(currentOrderNo);
            tagCrudService.updateById(latterEntity);
        });
    }
}
