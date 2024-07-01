package com.blog.biz.service.manager.impl;

import cn.hutool.core.io.FileUtil;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.biz.exception.BlogSyncException;
import com.blog.biz.model.context.PostParserContext;
import com.blog.biz.model.entity.*;
import com.blog.biz.service.crud.*;
import com.blog.biz.service.manager.BlogSyncService;
import com.blog.biz.service.manager.TaskManagerService;
import com.blog.biz.support.GitHelper;
import com.blog.biz.support.MarkdownParser;
import com.blog.common.properties.GitProjectProperties;
import com.blog.common.util.ColorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogSyncServiceImpl implements BlogSyncService {

    private final GitProjectProperties gitProjectProperties;

    private final CategoryCrudService categoryCrudService;

    private final PostCrudService postCrudService;

    private final PostContentCrudService postContentCrudService;

    private final TagCrudService tagCrudService;

    private final PostTagRelaCrudService postTagRelaCrudService;

    private final GitRepositoryCrudService gitRepositoryCrudService;

    private final TaskManagerService taskManagerService;

    @Override
    public void sync() {
        Long taskId = null;
        try {
            taskId = taskManagerService.recordRunTask("blog-sync", "开始同步博客文章");
            log.info("开始同步博客文章");
            doSync();
            log.info("同步博客文章完成");
            taskManagerService.recordEndTask(taskId, "同步博客文章完成");
        } catch (Exception ex) {
            log.error("同步博客文章发生异常", ex);
            taskManagerService.recordFailTask(taskId, ex, "博客文章同步失败");
        }
    }

    private void doSync() {
        GitRepositoryEntity gitRepository = getGitRepositoryEntity();

        log.info("开始从git上同步最新博客文章：{}", gitRepository.getUrl());
        syncNoteProject(gitRepository);
        log.info("从git上同步最新博客文章成功");

        List<File> files = FileUtil.loopFiles(gitRepository.getLocalPath(),
                file -> file.getName().endsWith(".md"));
        log.info("Markdown文章数量：{}", files.size());
        if (CollectionUtils.isEmpty(files)) {
            return;
        }

        // 查询所有文章分类
        List<CategoryEntity> allCategoryEntities = categoryCrudService.findAll();
        // 查询所有标签
        List<TagEntity> allTagEntities = tagCrudService.findAll();

        for (File file : files) {
            PostParserContext postParserContext = MarkdownParser.parse(file,
                    gitRepository.getLocalPath());

            // 同步分类
            CategoryEntity categoryEntity = syncCategory(postParserContext, allCategoryEntities);

            // 同步文章
            PostEntity postEntity = syncPost(postParserContext, categoryEntity);

            // 同步标签
            syncTag(postParserContext, postEntity, allTagEntities);
        }
    }

    /**
     * 同步git项目
     *
     * @param
     * @return void
     **/
    private void syncNoteProject(GitRepositoryEntity gitRepository) {
        GitHelper gitHelper = new GitHelper(gitRepository.getUrl(),
                gitRepository.getLocalPath(), gitRepository.getUsername(),
                gitRepository.getPassword());
        try {
            gitHelper.checkout(gitRepository.getBranch());
            gitHelper.pull();
        } finally {
            gitHelper.close();
        }
    }

    /**
     * 同步分类
     *
     * @param postParserContext
     * @param allCategoryEntities
     * @return CategoryEntity
     **/
    private CategoryEntity syncCategory(PostParserContext postParserContext, List<CategoryEntity> allCategoryEntities) {
        // 同步分类
        CategoryEntity parentCategoryEntity = null;
        if (CollectionUtils.isNotEmpty(postParserContext.getCategories())) {
            Map<String, CategoryEntity> existsCategoryMap = allCategoryEntities.stream()
                    .collect(Collectors.toMap(CategoryEntity::getCategoryName, Function.identity()));
            for (int index = 0, size = postParserContext.getCategories().size(); index < size; index++) {
                PostParserContext.Category category = postParserContext.getCategories().get(index);
                CategoryEntity categoryEntity;
                if (existsCategoryMap.containsKey(category.getCategoryName())) {
                    categoryEntity = existsCategoryMap.get(category.getCategoryName());
                } else {
                    categoryEntity = new CategoryEntity();
                    categoryEntity
                            .setParentCategoryId(
                                    Objects.isNull(parentCategoryEntity) ? 0L : parentCategoryEntity.getCategoryId())
                            .setCategoryName(category.getCategoryName())
                            .setFullId(Objects.isNull(parentCategoryEntity) ? "0"
                                    : (parentCategoryEntity.getFullId() + parentCategoryEntity.getCategoryId()))
                            .setOrderNo(category.getOrderNo())
                            .setLevel(category.getLevel());
                    categoryCrudService.save(categoryEntity);
                    allCategoryEntities.add(categoryEntity);
                }
                parentCategoryEntity = categoryEntity;
            }
        }
        return parentCategoryEntity;
    }

    /**
     * 同步文章
     *
     * @param postParserContext
     * @param categoryEntity
     * @return PostEntity
     **/
    private PostEntity syncPost(PostParserContext postParserContext, CategoryEntity categoryEntity) {
        // 同步文章
        PostEntity postEntity = postCrudService.findByTitle(postParserContext.getTitle()).orElse(null);
        // 是否需要新增
        boolean add = Objects.isNull(postEntity);
        // 是否需要更新
        boolean update = false;

        PostContentEntity postContentEntity = add ? new PostContentEntity()
                : postContentCrudService.getByField(PostContentEntity::getPostId, postEntity.getPostId())
                .orElse(new PostContentEntity());

        if (add) {
            postEntity = new PostEntity();
            postEntity.setTitle(postParserContext.getTitle())
                    .setSummary(postParserContext.getSummary())
                    .setSource(PostSource.MD_SYNC)
                    .setStatus(PostStatus.DRAFT)
                    .setEnableComment(Boolean.TRUE)
                    .setTop(Boolean.FALSE)
                    .setCoverPictureUrl(postParserContext.getCoverPictureUrl())
                    .setFileLastUpdateTime(postParserContext.getFileLastUpdate());
            if (Objects.nonNull(categoryEntity)) {
                postEntity.setCategoryId(categoryEntity.getCategoryId());
            }
            postCrudService.save(postEntity);
        } else {
            if (postParserContext.getFileLastUpdate().isAfter(postEntity.getFileLastUpdateTime())) {
                postEntity.setTitle(postParserContext.getTitle())
                        .setSummary(postParserContext.getSummary())
                        .setFileLastUpdateTime(postParserContext.getFileLastUpdate());
                if (Objects.nonNull(categoryEntity)) {
                    postEntity.setCategoryId(categoryEntity.getCategoryId());
                }
                postCrudService.updateById(postEntity);
                update = true;
            }
        }

        if (add || update) {
            postContentEntity.setPostId(postEntity.getPostId()).setContent(postParserContext.getContent());
            postContentCrudService.saveOrUpdate(postContentEntity);
        }

        return postEntity;
    }

    /**
     * 同步标签
     *
     * @param postParserContext
     * @param postEntity
     * @param allTagEntities
     * @return void
     **/
    private void syncTag(PostParserContext postParserContext, PostEntity postEntity, List<TagEntity> allTagEntities) {
        if (CollectionUtils.isNotEmpty(postParserContext.getTags())) {
            List<TagEntity> toCreateTagEntities = postParserContext.getTags()
                    .stream()
                    .filter(tagName -> allTagEntities.stream()
                            .filter(tag -> tag.getTagName().equals(tagName))
                            .findFirst()
                            .isEmpty())
                    .map(tagName -> {
                        TagEntity tagEntity = new TagEntity();
                        tagEntity.setTagName(tagName).setColor(ColorUtil.generateHexColor());
                        return tagEntity;
                    })
                    .collect(Collectors.toList());
            tagCrudService.saveBatch(toCreateTagEntities);
            allTagEntities.addAll(toCreateTagEntities);

            List<PostTagRelaEntity> postTagRelaEntities = allTagEntities.stream()
                    .filter(entity -> postParserContext.getTags().contains(entity.getTagName()))
                    .map(entity -> {
                        PostTagRelaEntity postTagRelaEntity = new PostTagRelaEntity();
                        postTagRelaEntity.setPostId(postEntity.getPostId()).setTagId(entity.getTagId());
                        return postTagRelaEntity;
                    })
                    .collect(Collectors.toList());
            postTagRelaCrudService.removeByField(PostTagRelaEntity::getPostId, postEntity.getPostId());
            if (CollectionUtils.isNotEmpty(postTagRelaEntities)) {
                postTagRelaCrudService.saveBatch(postTagRelaEntities);
            }
        }
    }

    private GitRepositoryEntity getGitRepositoryEntity() {
        return gitRepositoryCrudService.list().stream().findFirst().orElseThrow(() -> new BlogSyncException("Git仓库信息未配置"));
    }
}
