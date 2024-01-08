package com.blog.biz.service.manager.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.biz.convert.PostConverter;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.biz.model.entity.PostContentEntity;
import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.model.entity.PostTagRelaEntity;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.request.PagePostRequest;
import com.blog.biz.model.response.CreatePostResponse;
import com.blog.biz.model.response.PagePostResponse;
import com.blog.biz.service.crud.*;
import com.blog.biz.service.manager.PostManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.exception.BusinessException;
import com.blog.common.properties.SecurityProperties;

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
        PostEntity entity = PostConverter.INSTANCE.toEntity(request);
        entity.setSource(PostSource.ADD).setStatus(request.getPublish() ? PostStatus.PUBLISHED : PostStatus.DRAFT)
            .setPublishTime(request.getPublish() ? LocalDateTime.now() : null)
            // todo: 统计markdown文章字数
            .setWordCount(0);;

        if (StringUtils.isNotBlank(entity.getPassword())) {
            // 密码加密
            entity.setPassword(
                SaSecureUtil.rsaEncryptByPublic(securityProperties.getRsa().getPublicKey(), entity.getPassword()));
        }

        // 校验分类信息是否存在
        categoryCrudService.getOptById(entity.getCategoryId())
            .orElseThrow(() -> new BusinessException("分类信息不存在或已被删除"));

        // 保存文章基本信息
        postCrudService.save(entity);

        // 保存文章内容
        PostContentEntity postContentEntity = new PostContentEntity();
        postContentEntity.setContent(request.getContent());
        postContentCrudService.save(postContentEntity);

        if (CollectionUtils.isNotEmpty(request.getTagIds())) {
            List<TagEntity> tagEntities = tagCrudService.listByIds(request.getTagIds());
            if (CollectionUtils.isEmpty(tagEntities) || request.getTagIds().size() != tagEntities.size()) {
                throw new BusinessException("存在未知的标签，请检查");
            }
            List<PostTagRelaEntity> postTagRelaEntities = request.getTagIds().stream().map(tagId -> {
                PostTagRelaEntity postTagRelaEntity = new PostTagRelaEntity();
                postTagRelaEntity.setPostId(entity.getPostId()).setTagId(tagId);
                return postTagRelaEntity;
            }).collect(Collectors.toList());
            // 保存文章标签关系
            postTagRelaCrudService.saveBatch(postTagRelaEntities);
        }
        return new CreatePostResponse(entity.getPostId());
    }

    @Override
    public PageResponse<PagePostResponse> page(PagePostRequest request) {
        return null;
    }

    @Override
    public void publish(Long postId) {
        PostEntity entity = postCrudService.getOneOrThrow(postId);
        entity.setStatus(PostStatus.PUBLISHED).setPublishTime(LocalDateTime.now());
        postCrudService.updateById(entity);
    }

    @Override
    public void unpublish(Long postId) {
        PostEntity entity = postCrudService.getOneOrThrow(postId);
        entity.setStatus(PostStatus.UNPUBLISHED);
        postCrudService.updateById(entity);
    }
}
