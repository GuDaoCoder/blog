package com.blog.biz.service.manager.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.convert.PostConverter;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.biz.model.context.PagePostContext;
import com.blog.biz.model.entity.*;
import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.request.PagePostRequest;
import com.blog.biz.model.request.UpdatePostRequest;
import com.blog.biz.model.response.CreatePostResponse;
import com.blog.biz.model.response.PagePostResponse;
import com.blog.biz.model.response.PageTagResponse;
import com.blog.biz.service.crud.*;
import com.blog.biz.service.manager.PostManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.exception.BusinessException;
import com.blog.common.properties.SecurityProperties;
import com.blog.common.util.PageUtil;
import com.blog.common.util.StreamUtil;

import cn.dev33.satoken.secure.SaSecureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PostManagerServiceImpl implements PostManagerService {

    private final SecurityProperties securityProperties;

    private final CategoryCrudService categoryCrudService;

    private final PostCrudService postCrudService;

    private final PostTagRelaCrudService postTagRelaCrudService;

    private final TagCrudService tagCrudService;

    private final PostContentCrudService postContentCrudService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CreatePostResponse create(CreatePostRequest request) {
        // 校验分类信息是否存在
        categoryCrudService.getOptById(request.getCategoryId())
                .orElseThrow(() -> new BusinessException("分类信息不存在或已被删除"));

        PostEntity entity = PostConverter.INSTANCE.toEntity(request);
        entity.setSource(PostSource.ADD).setStatus(request.getPublish() ? PostStatus.PUBLISHED : PostStatus.DRAFT)
                .setPublishTime(request.getPublish() ? LocalDateTime.now() : null)
                // todo: 统计markdown文章字数
                .setWordCount(0);
        // 加密密码
        encryptPassword(entity, request.getPassword());

        // 保存文章基本信息
        postCrudService.save(entity);

        // 保存文章内容
        PostContentEntity postContentEntity = new PostContentEntity();
        postContentEntity.setPostId(entity.getPostId()).setContent(request.getContent());
        postContentCrudService.save(postContentEntity);

        // 保存文章标签关系
        savePostTagRelas(entity.getPostId(), request.getTagIds());
        return new CreatePostResponse(entity.getPostId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Long postId, UpdatePostRequest request) {
        // 校验分类信息是否存在
        categoryCrudService.getOptById(request.getCategoryId())
                .orElseThrow(() -> new BusinessException("分类信息不存在或已被删除"));

        // 文章基础信息
        PostEntity entity = postCrudService.getOneOrThrow(postId);
        entity.setTitle(request.getTitle()).setSummary(request.getSummary())
                .setCoverPictureUrl(request.getCoverPictureUrl()).setCategoryId(request.getCategoryId())
                .setTop(request.getTop()).setEnableComment(request.getEnableComment());

        // 加密密码
        encryptPassword(entity, request.getPassword());
        postCrudService.updateById(entity);

        // 文章内容信息
        PostContentEntity postContentEntity = postContentCrudService.getByField(PostContentEntity::getPostId, postId)
                .orElseThrow(() -> new BusinessException("文章内容信息不存在或已被删除"));
        postContentEntity.setContent(request.getContent());
        postContentCrudService.updateById(postContentEntity);

        // 标签信息
        postTagRelaCrudService.removeByField(PostTagRelaEntity::getPostId, postId);
        if (CollectionUtils.isNotEmpty(request.getTagIds())) {
            savePostTagRelas(postId, request.getTagIds());
        }
    }

    @Override
    public void delete(Long postId) {
        postCrudService.getOneOrThrow(postId);

        // 删除文章基本信息
        postCrudService.removeById(postId);
        // 删除文章内容
        postContentCrudService.removeByField(PostContentEntity::getPostId, postId);
        // 删除文章标签关系
        postTagRelaCrudService.removeByField(PostTagRelaEntity::getPostId, postId);
    }

    @Override
    public PageResponse<PagePostResponse> page(PagePostRequest request) {
        PagePostContext pageContext = PostConverter.INSTANCE.toPageContext(request);
        pageContext.setPageable(PageUtil.pageable(request));
        IPage<PostEntity> page = postCrudService.page(pageContext);

        Map<Long, CategoryEntity> categoryMap = new HashMap<>();
        Map<Long, List<TagEntity>> tagMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            List<Long> categoryIds = StreamUtil.mapField(page.getRecords(), PostEntity::getCategoryId);
            categoryMap = categoryCrudService.listByIds(categoryIds).stream()
                    .collect(Collectors.toMap(CategoryEntity::getCategoryId, Function.identity()));

            List<Long> postIds = StreamUtil.mapField(page.getRecords(), PostEntity::getPostId);
            tagMap = postTagsRela(postIds);
        }

        Map<Long, CategoryEntity> finalCategoryMap = categoryMap;
        Map<Long, List<TagEntity>> finalTagMap = tagMap;
        return PageUtil.toResult(page, entity -> {
            PagePostResponse pagePostResponse = PostConverter.INSTANCE.toPagePostResponse(entity);
            Optional.ofNullable(finalCategoryMap.get(entity.getCategoryId())).map(CategoryEntity::getCategoryName)
                    .ifPresent(pagePostResponse::setCategoryName);

            Optional.ofNullable(finalTagMap.get(entity.getPostId())).ifPresent(list -> {
                List<PagePostResponse.TagItem> tagItems = list
                        .stream()
                        .map(o -> new PagePostResponse.TagItem(o.getTagName(), o.getColor()))
                        .collect(Collectors.toList());
                pagePostResponse.setTags(tagItems);
            });
            return pagePostResponse;
        });
    }

    @Override
    public void moveRecycleBin(Long postId) {
        PostEntity entity = postCrudService.getOneOrThrow(postId);
        entity.setStatus(PostStatus.RECYCLE_BIN);
        postCrudService.updateById(entity);
    }

    @Override
    public void publish(Long postId) {
        PostEntity entity = postCrudService.getOneOrThrow(postId);
        entity.setStatus(PostStatus.PUBLISHED).setPublishTime(LocalDateTime.now());
        postCrudService.updateById(entity);
    }

    @Override
    public void unpublished(Long postId) {
        PostEntity entity = postCrudService.getOneOrThrow(postId);
        entity.setStatus(PostStatus.UNPUBLISHED);
        postCrudService.updateById(entity);
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
     * 加密密码
     *
     * @param entity   PostEntity
     * @param password String
     */
    private void encryptPassword(PostEntity entity, String password) {
        if (StringUtils.isNotBlank(password)) {
            // 密码加密
            entity.setPassword(
                    SaSecureUtil.rsaEncryptByPublic(securityProperties.getRsa().getPublicKey(), entity.getPassword()));
        } else {
            entity.setPassword(null);
        }
    }

    /**
     * 保存文章标签关系
     *
     * @param postId Long
     * @param tagIds Long>
     */
    private void savePostTagRelas(Long postId, List<Long> tagIds) {
        if (CollectionUtils.isNotEmpty(tagIds)) {
            List<TagEntity> tagEntities = tagCrudService.listByIds(tagIds);
            if (CollectionUtils.isEmpty(tagEntities) || tagIds.size() != tagEntities.size()) {
                throw new BusinessException("存在未知的标签，请检查");
            }
            List<PostTagRelaEntity> postTagRelaEntities = tagIds.stream().map(tagId -> {
                PostTagRelaEntity postTagRelaEntity = new PostTagRelaEntity();
                postTagRelaEntity.setPostId(postId).setTagId(tagId);
                return postTagRelaEntity;
            }).collect(Collectors.toList());
            // 保存文章标签关系
            postTagRelaCrudService.saveBatch(postTagRelaEntities);
        }
    }
}
