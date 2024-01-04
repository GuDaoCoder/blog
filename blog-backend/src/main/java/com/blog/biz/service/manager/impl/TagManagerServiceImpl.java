package com.blog.biz.service.manager.impl;

import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.blog.biz.convert.TagConverter;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.PageTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
import com.blog.biz.model.response.CreateTagResponse;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.service.crud.TagCrudService;
import com.blog.biz.service.manager.TagManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.exception.BusinessException;
import com.blog.common.util.JpaUtil;

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

    @Override
    public CreateTagResponse add(CreateTagRequest request) {
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
        return new CreateTagResponse(tagCrudService.saveOrUpdate(entity));
    }

    @Override
    public void update(Long tagId, UpdateTagRequest request) {
        TagEntity entity = tagCrudService.findOneByIdOrThrow(tagId);
        if (!StringUtils.equals(request.getTagName(), entity.getTagName())) {
            TagEntity existsTagEntity = tagCrudService.findByTagName(request.getTagName()).orElse(null);
            if (Objects.nonNull(existsTagEntity)) {
                throw new BusinessException("标签名称不能重复");
            }
        }
        entity.setTagName(request.getTagName());
        tagCrudService.saveOrUpdate(entity);
    }

    @Override
    public PageResponse<TagResponse> page(PageTagRequest request) {
        Page<TagEntity> page = tagCrudService.page(request.getTagName(), request.pageable());
        return JpaUtil.toPageResult(page, TagConverter.INSTANCE::toResponse);
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void moveUp(Long tagId) {
        TagEntity entity = tagCrudService.findOneByIdOrThrow(tagId);
        Integer currentOrderNo = entity.getOrderNo();
        tagCrudService.findPrevious(currentOrderNo).ifPresent(previousEntity -> {
            entity.setOrderNo(previousEntity.getOrderNo());
            tagCrudService.saveOrUpdate(entity);

            previousEntity.setOrderNo(currentOrderNo);
            tagCrudService.saveOrUpdate(previousEntity);
        });
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public void moveDown(Long tagId) {
        TagEntity entity = tagCrudService.findOneByIdOrThrow(tagId);
        Integer currentOrderNo = entity.getOrderNo();
        tagCrudService.findLatter(currentOrderNo).ifPresent(latterEntity -> {
            entity.setOrderNo(latterEntity.getOrderNo());
            tagCrudService.saveOrUpdate(entity);

            latterEntity.setOrderNo(currentOrderNo);
            tagCrudService.saveOrUpdate(latterEntity);
        });
    }
}
