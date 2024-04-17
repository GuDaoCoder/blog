package com.blog.biz.service.manager.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.convert.PostConverter;
import com.blog.biz.convert.TagConverter;
import com.blog.biz.enums.PostStatus;
import com.blog.biz.model.context.SearchPostContext;
import com.blog.biz.model.entity.*;
import com.blog.biz.model.request.PostSearchRequest;
import com.blog.biz.model.request.PostPortalSearchRequest;
import com.blog.biz.model.response.PostDetailResponse;
import com.blog.biz.model.response.PostResponse;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.service.crud.*;
import com.blog.biz.service.manager.PostManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.exception.BusinessException;
import com.blog.common.util.PageUtil;
import com.blog.common.util.StreamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PostManagerServiceImpl implements PostManagerService {

    private final CategoryCrudService categoryCrudService;

    private final PostCrudService postCrudService;

    private final PostTagRelaCrudService postTagRelaCrudService;

    private final PostContentCrudService postContentCrudService;

    private final TagCrudService tagCrudService;

    @Override
    public PageResponse<PostResponse> search(PostSearchRequest request) {
        SearchPostContext searchPostContext = PostConverter.INSTANCE.toPageContext(request);
        searchPostContext.setPageable(PageUtil.pageable(request));
        IPage<PostEntity> page = postCrudService.page(searchPostContext);
        return PageUtil.result(page, toPostResponses(page.getRecords()));
    }

    @Override
    public PageResponse<PostResponse> blogSearch(PostPortalSearchRequest request) {
        SearchPostContext searchPostContext = PostConverter.INSTANCE.toPageContext(request);
        searchPostContext.setStatus(PostStatus.PUBLISHED);
        searchPostContext.setPageable(PageUtil.pageable(request));
        IPage<PostEntity> page = postCrudService.page(searchPostContext);
        return PageUtil.result(page, toPostResponses(page.getRecords()));
    }

    @Override
    public void publish(Long postId) {
        PostEntity existsPostEntity = postCrudService.getOneOrThrow(postId);
        if (!canPublish(existsPostEntity.getStatus())) {
            throw new BusinessException("只有草稿和已下架的文章才可以发布");
        }
        existsPostEntity.setStatus(PostStatus.PUBLISHED).setPublishTime(LocalDateTime.now());
        postCrudService.updateById(existsPostEntity);
    }

    @Override
    public void remove(Long postId) {
        PostEntity existsPostEntity = postCrudService.getOneOrThrow(postId);
        if (!canRemove(existsPostEntity.getStatus())) {
            throw new BusinessException("只有已发布的文章才可以下架");
        }
        existsPostEntity.setStatus(PostStatus.REMOVED).setRemoveTime(LocalDateTime.now());
        postCrudService.updateById(existsPostEntity);
    }

    @Override
    public String getContent(Long postId) {
        postCrudService.getOneOrThrow(postId);
        return postContentCrudService.getByField(PostContentEntity::getPostId, postId)
                .map(PostContentEntity::getContent)
                .orElse(null);
    }

    @Override
    public void updateCoverPicture(Long postId, String coverPictureUrl) {
        PostEntity postEntity = postCrudService.getOneOrThrow(postId);
        postEntity.setCoverPictureUrl(coverPictureUrl);
        postCrudService.updateById(postEntity);
    }

    @Override
    public PostDetailResponse detail(Long postId) {
        PostEntity postEntity = postCrudService.getOneOrThrow(postId);
        PostDetailResponse postDetailResponse = PostConverter.INSTANCE.toDetailResponse(toPostResponse(postEntity));
        postContentCrudService.findPostContentByPostId(postId)
                .ifPresent(postContentEntity -> postDetailResponse.setContent(postContentEntity.getContent()));
        return postDetailResponse;
    }

    private PostResponse toPostResponse(PostEntity postEntity) {
        return CollUtil.getFirst(toPostResponses(List.of(postEntity)));
    }

    private List<PostResponse> toPostResponses(List<PostEntity> postEntities) {
        if (CollectionUtils.isEmpty(postEntities)) {
            return new ArrayList<>();
        }
        Map<Long, CategoryEntity> categoryMap = categoryCrudService.listByIds(
                postEntities.stream()
                        .map(PostEntity::getCategoryId)
                        .collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(CategoryEntity::getCategoryId, categoryEntity -> categoryEntity));

        Map<Long, List<TagEntity>> tagMap = postTagsRela(postEntities.stream().map(PostEntity::getPostId).collect(Collectors.toList()));
        return postEntities
                .stream()
                .map(postEntity -> {
                    PostResponse postResponse = PostConverter.INSTANCE.toResponse(postEntity);
                    Optional.ofNullable(categoryMap.get(postEntity.getCategoryId()))
                            .map(CategoryEntity::getCategoryName)
                            .ifPresent(postResponse::setCategoryName);

                    Optional.ofNullable(tagMap.get(postEntity.getPostId())).ifPresent(list -> {
                        List<TagResponse> tags = list
                                .stream()
                                .map(TagConverter.INSTANCE::toResponse)
                                .collect(Collectors.toList());
                        postResponse.setTags(tags);
                    });
                    return postResponse;
                }).toList();
    }

    /**
     * 查询文章和标签关系
     *
     * @param postIds Long>
     * @return java.util.Map<java.lang.Long, java.util.List < com.blog.biz.model.entity.TagEntity>>
     */
    private Map<Long, List<TagEntity>> postTagsRela(List<Long> postIds) {
        if (CollectionUtils.isEmpty(postIds)) {
            return new HashMap<>();
        }
        List<PostTagRelaEntity> postTagRelaEntities =
                postTagRelaCrudService.listByFields(PostTagRelaEntity::getPostId, postIds);
        if (CollectionUtils.isEmpty(postTagRelaEntities)) {
            return new HashMap<>();
        }
        List<Long> tagIds = StreamUtil.mapField(postTagRelaEntities, PostTagRelaEntity::getTagId);
        Map<Long, TagEntity> tagMap = tagCrudService.listByIds(tagIds).stream()
                .collect(Collectors.toMap(TagEntity::getTagId, Function.identity()));
        return postTagRelaEntities.stream().collect(Collectors.groupingBy(PostTagRelaEntity::getPostId,
                Collectors.mapping(o -> tagMap.get(o.getTagId()), Collectors.toList())));
    }

    /**
     * 判断文章是否可以发布。
     *
     * @param postStatus 文章的状态，代表文章当前的状态。
     * @return 返回一个布尔值，如果文章状态为草稿（DRAFT）或已移除（REMOVED），则可以发布，返回true；否则返回false。
     */
    private boolean canPublish(PostStatus postStatus) {
        // 判断文章状态是否为草稿或已移除
        return PostStatus.DRAFT.equals(postStatus) || PostStatus.REMOVED.equals(postStatus);
    }


    /**
     * 判断是否可以移除文章。
     *
     * @param postStatus 文章的状态，代表文章当前的状态。
     * @return 返回一个布尔值，如果文章状态为已发布（PUBLISHED），则返回true，否则返回false。
     */
    private boolean canRemove(PostStatus postStatus) {
        // 判断传入的文章状态是否等于已发布
        return PostStatus.PUBLISHED.equals(postStatus);
    }
}

