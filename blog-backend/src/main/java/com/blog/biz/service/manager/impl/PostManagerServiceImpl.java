package com.blog.biz.service.manager.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.blog.biz.convert.PostConverter;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.model.entity.PostTagRelaEntity;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.response.CreatePostResponse;
import com.blog.biz.service.crud.CategoryCrudService;
import com.blog.biz.service.crud.PostCrudService;
import com.blog.biz.service.crud.PostTagRelaCrudService;
import com.blog.biz.service.crud.TagCrudService;
import com.blog.biz.service.manager.PostManagerService;
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

    @Transactional(rollbackOn = RuntimeException.class)
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
        categoryCrudService.findOneById(entity.getCategoryId())
            .orElseThrow(() -> new BusinessException("分类信息不存在或已被删除"));

        // 保存文章
        postCrudService.save(entity);

        if (CollectionUtils.isNotEmpty(request.getTagIds())) {
            List<TagEntity> tagEntities = tagCrudService.findAllByIds(request.getTagIds());
            if (CollectionUtils.isEmpty(tagEntities) || request.getTagIds().size() != tagEntities.size()) {
                throw new BusinessException("存在未知的标签，请检查");
            }
            List<PostTagRelaEntity> postTagRelaEntities = request.getTagIds().stream().map(tagId -> {
                PostTagRelaEntity postTagRelaEntity = new PostTagRelaEntity();
                postTagRelaEntity.setPostId(entity.getPostId()).setTagId(tagId);
                return postTagRelaEntity;
            }).collect(Collectors.toList());
            // 保存文章标签关系
            postTagRelaCrudService.saveAll(postTagRelaEntities);
        }
        return new CreatePostResponse(entity.getPostId());
    }

    @Override
    public void publish(Long postId) {
        PostEntity entity = postCrudService.findOneByIdOrThrow(postId);
        entity.setStatus(PostStatus.PUBLISHED).setPublishTime(LocalDateTime.now());
        postCrudService.save(entity);
    }

    @Override
    public void unpublish(Long postId) {
        PostEntity entity = postCrudService.findOneByIdOrThrow(postId);
        entity.setStatus(PostStatus.UNPUBLISHED);
        postCrudService.save(entity);
    }
}
