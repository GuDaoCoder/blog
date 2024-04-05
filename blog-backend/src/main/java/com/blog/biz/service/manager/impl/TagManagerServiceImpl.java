package com.blog.biz.service.manager.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.convert.TagConverter;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.entity.custom.TagPostCountEntity;
import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.SearchTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
import com.blog.biz.model.response.TagDetailResponse;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.service.crud.PostTagRelaCrudService;
import com.blog.biz.service.crud.TagCrudService;
import com.blog.biz.service.manager.TagManagerService;
import com.blog.common.base.response.PageResponse;
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
    public PageResponse<TagDetailResponse> search(SearchTagRequest request) {
        IPage<TagEntity> page = tagCrudService.page(request.getTagName(), PageUtil.pageable(request));
        return PageUtil.result(page, toDetailResponses(page.getRecords()));
    }

    private List<TagDetailResponse> toDetailResponses(List<TagEntity> tagEntities) {
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
                    TagDetailResponse tagDetailResponse = TagConverter.INSTANCE.toDetailResponse(tagEntity);
                    tagDetailResponse.setPostCount(tagPostCountMap.getOrDefault(tagEntity.getTagId(), 0L));
                    return tagDetailResponse;
                }).toList();
    }
}
