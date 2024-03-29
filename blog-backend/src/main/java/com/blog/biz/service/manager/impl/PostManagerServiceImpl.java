package com.blog.biz.service.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.convert.PostConverter;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.biz.model.context.SearchPostContext;
import com.blog.biz.model.entity.*;
import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.request.SearchPostRequest;
import com.blog.biz.model.request.UpdatePostRequest;
import com.blog.biz.model.request.blog.SearchPostBlogRequest;
import com.blog.biz.model.response.PostResponse;
import com.blog.biz.model.response.blog.PostBlogResponse;
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
import org.springframework.transaction.annotation.Transactional;

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
    public PageResponse<PostResponse> search(SearchPostRequest request) {
        SearchPostContext searchPostContext = PostConverter.INSTANCE.toPageContext(request);
        searchPostContext.setPageable(PageUtil.pageable(request));
        IPage<PostEntity> page = postCrudService.page(searchPostContext);
        return PageUtil.result(page, toPostResponses(page.getRecords()));
    }

    @Override
    public PageResponse<PostBlogResponse> blogSearch(SearchPostBlogRequest request) {
        SearchPostContext searchPostContext = PostConverter.INSTANCE.toPageContext(request);
        searchPostContext.setPageable(PageUtil.pageable(request));
        IPage<PostEntity> page = postCrudService.page(searchPostContext);
        return PageUtil.result(page, toPostBlogResponses(page.getRecords()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostResponse create(CreatePostRequest request) {
        // 校验分类信息是否存在
        categoryCrudService.getOptById(request.getCategoryId())
                .orElseThrow(() -> new BusinessException("分类信息不存在或已被删除"));

        PostEntity postEntity = PostConverter.INSTANCE.toEntity(request);
        postEntity.setSource(PostSource.ADD)
                .setStatus(request.getPublish() ? PostStatus.PUBLISHED : PostStatus.DRAFT)
                .setPublishTime(PostStatus.PUBLISHED.equals(postEntity.getStatus()) ? LocalDateTime.now() : null);

        // 保存文章基本信息
        postCrudService.save(postEntity);

        // 保存文章内容
        postContentCrudService.saveContent(postEntity.getPostId(), request.getContent());

        // 保存标签信息
        savePostTagRelation(postEntity.getPostId(), request.getTagIds());
        return toPostResponse(postEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostResponse update(Long postId, UpdatePostRequest request) {
        // 校验文章信息
        PostEntity existsPostEntity = postCrudService.getOneOrThrow(postId);

        // 校验分类信息是否存在
        categoryCrudService.getOptById(request.getCategoryId())
                .orElseThrow(() -> new BusinessException("分类信息不存在或已被删除"));

        PostEntity postEntity = PostConverter.INSTANCE.toEntity(request);
        postEntity.setPostId(postId);
        // 是否可以直接发布
        if (request.getPublish() && canPublish(existsPostEntity.getStatus())) {
            postEntity.setStatus(PostStatus.PUBLISHED);
        }
        // 更新文章基本信息
        postCrudService.updateById(postEntity);

        // 更新文章内容
        postContentCrudService.updateContentByPostId(postId, request.getContent());

        // 更新标签信息
        postTagRelaCrudService.removeByField(PostTagRelaEntity::getPostId, postId);
        savePostTagRelation(postId, request.getTagIds());

        return toPostResponse(postCrudService.getById(postId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long postId) {
        postCrudService.getOneOrThrow(postId);

        // 删除文章基本信息
        postCrudService.removeById(postId);
        // 删除文章标签关系
        postTagRelaCrudService.removeByField(PostTagRelaEntity::getPostId, postId);
    }


    @Override
    public void moveRecycleBin(Long postId) {
        PostEntity existsPostEntity = postCrudService.getOneOrThrow(postId);
        existsPostEntity.setStatus(PostStatus.RECYCLE_BIN).setDeleteTime(LocalDateTime.now());
        postCrudService.updateById(existsPostEntity);
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
        existsPostEntity.setStatus(PostStatus.DRAFT).setRemoveTime(LocalDateTime.now());
        postCrudService.updateById(existsPostEntity);
    }

    @Override
    public String getPostContent(Long postId) {
        postCrudService.getOneOrThrow(postId);
        return postContentCrudService.getByField(PostContentEntity::getPostId, postId)
                .map(PostContentEntity::getContent)
                .orElse(null);
    }

    private List<PostBlogResponse> toPostBlogResponses(List<PostEntity> postEntities) {
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
                    PostBlogResponse postBlogResponse = PostConverter.INSTANCE.toBlogResponse(postEntity);
                    Optional.ofNullable(categoryMap.get(postEntity.getCategoryId()))
                            .map(CategoryEntity::getCategoryName)
                            .ifPresent(postBlogResponse::setCategoryName);

                    Optional.ofNullable(tagMap.get(postEntity.getPostId())).ifPresent(list -> {
                        List<PostBlogResponse.TagItem> tagItems = list
                                .stream()
                                .map(o -> new PostBlogResponse.TagItem(o.getTagId(), o.getTagName(), o.getColor()))
                                .collect(Collectors.toList());
                        postBlogResponse.setTags(tagItems);
                    });
                    return postBlogResponse;
                }).toList();
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
                        List<PostResponse.TagItem> tagItems = list
                                .stream()
                                .map(o -> new PostResponse.TagItem(o.getTagId(), o.getTagName(), o.getColor()))
                                .collect(Collectors.toList());
                        postResponse.setTags(tagItems);
                    });
                    return postResponse;
                }).toList();
    }

    private PostResponse toPostResponse(PostEntity postEntity) {
        List<PostResponse> postResponses = toPostResponses(List.of(postEntity));
        return postResponses.stream().findFirst().orElse(null);
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
     * 保存文章标签关系
     *
     * @param postId
     * @param tagIds
     */
    private void savePostTagRelation(Long postId, List<Long> tagIds) {
        if (CollectionUtils.isEmpty(tagIds)) {
            return;
        }

        List<PostTagRelaEntity> postTagRelaEntities = tagIds.stream().map(tagId -> {
            PostTagRelaEntity postTagRelaEntity = new PostTagRelaEntity();
            postTagRelaEntity.setPostId(postId).setTagId(tagId);
            return postTagRelaEntity;
        }).collect(Collectors.toList());

        // 保存文章标签关系
        postTagRelaCrudService.saveBatch(postTagRelaEntities);
    }

    /**
     * 判断是否可以发布
     *
     * @param postStatus
     * @return boolean
     **/
    private boolean canPublish(PostStatus postStatus) {
        return PostStatus.DRAFT.equals(postStatus) || PostStatus.REMOVED.equals(postStatus);
    }

    /**
     * 判断是否可以下架
     *
     * @param postStatus
     * @return boolean
     **/
    private boolean canRemove(PostStatus postStatus) {
        return PostStatus.PUBLISHED.equals(postStatus);
    }
}
