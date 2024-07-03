package com.blog.biz.service.manager.impl;

import cn.hutool.core.io.FileUtil;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.biz.enums.TaskStatus;
import com.blog.biz.exception.BlogSyncException;
import com.blog.biz.model.context.PostParserContext;
import com.blog.biz.model.entity.*;
import com.blog.biz.service.crud.*;
import com.blog.biz.service.manager.BlogSyncService;
import com.blog.biz.service.manager.TaskManagerService;
import com.blog.biz.support.MarkdownParser;
import com.blog.biz.sync.TaskSyncProgressSender;
import com.blog.biz.sync.WebSocketSyncProgressSender;
import com.blog.common.support.GitOperation;
import com.blog.common.util.ColorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Scope("prototype")
@Slf4j
@RequiredArgsConstructor
@Service
public class BlogSyncServiceImpl implements BlogSyncService {

    private final CategoryCrudService categoryCrudService;

    private final PostCrudService postCrudService;

    private final PostContentCrudService postContentCrudService;

    private final TagCrudService tagCrudService;

    private final PostTagRelaCrudService postTagRelaCrudService;

    private final GitRepositoryCrudService gitRepositoryCrudService;

    private final TaskManagerService taskManagerService;

    private final List<GitOperation.GitOperationLogSender> syncProgressSenders = new ArrayList<>();

    private Long taskId = null;

    @Override
    public void sync() {
        sync(null);
    }

    @Override
    public void sync(WebSocketSession session) {
        syncProgressSenders.add(new TaskSyncProgressSender());
        if (session != null) {
            syncProgressSenders.add(new WebSocketSyncProgressSender(session));
        }

        TaskStatus taskStatus = TaskStatus.SUCCESS;
        try {
            taskId = taskManagerService.startNewTask("blog-sync");
            syncTaskMonitor(">>>>>>>>>>>>>>>>>>>>开始同步文章");
            doSync();
            syncTaskMonitor(">>>>>>>>>>>>>>>>>>>>同步文章结束");
        }
        catch (Exception ex) {
            taskStatus = TaskStatus.FAIL;
            syncTaskMonitor(ex, ">>>>>>>>>>>>>>>>>>>>同步文章发生异常");
        }
        finally {
            TaskSyncProgressSender taskSyncProgressSender = (TaskSyncProgressSender) syncProgressSenders.get(0);
            taskManagerService.endTask(taskId, taskStatus, taskSyncProgressSender.getLog().toString());

        }
    }

    private void doSync() throws GitAPIException, IOException {
        GitRepositoryEntity gitRepository = getGitRepositoryEntity();
        syncTaskMonitor(">>>>>>>>>>>>>>>>>>>>获取git信息成功");

        syncTaskMonitor(">>>>>>>>>>>>>>>>>>>>开始从git上同步最新博客文章：{}", gitRepository.getUrl());
        syncNoteProject(gitRepository);
        syncTaskMonitor(">>>>>>>>>>>>>>>>>>>>从git上同步最新博客文章成功");

        List<File> files = FileUtil.loopFiles(gitRepository.getLocalPath(), file -> file.getName().endsWith(".md"));
        syncTaskMonitor(">>>>>>>>>>>>>>>>>>>>git仓库中共计{}篇文章", files.size());

        if (CollectionUtils.isEmpty(files)) {
            return;
        }

        // 查询所有文章分类
        List<CategoryEntity> allCategoryEntities = categoryCrudService.findAll();
        // 查询所有标签
        List<TagEntity> allTagEntities = tagCrudService.findAll();

        for (File file : files) {
            PostParserContext postParserContext = MarkdownParser.parse(file, gitRepository.getLocalPath());

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
     * @param
     * @return void
     **/
    private void syncNoteProject(GitRepositoryEntity gitRepository) throws GitAPIException, IOException {
        new GitOperation.Builder().gitRepositoryUrl(gitRepository.getUrl())
            .localRepositoryPath(gitRepository.getLocalPath())
            .branch(gitRepository.getBranch())
            .username(gitRepository.getUsername())
            .password(gitRepository.getPassword())
            .operationLogSenders(syncProgressSenders)
            .build()
            .cloneOrUpdateRepository();
    }

    /**
     * 同步分类
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
                }
                else {
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
        }
        else {
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

    // todo:缓存优化
    private GitRepositoryEntity getGitRepositoryEntity() {
        return gitRepositoryCrudService.list()
            .stream()
            .findFirst()
            .orElseThrow(() -> new BlogSyncException("Git仓库信息未配置"));
    }

    private void syncTaskMonitor(String message, Object... ars) {
        log.info(message, ars);
        syncProgressSenders.forEach(sender -> {
            try {
                sender.send(MessageFormatter.arrayFormat(message, ars).getMessage() + "\n");
            }
            catch (Exception e) {
                log.warn("{} send blog sync monitor info error", sender.getClass().getName(), e);
            }
        });
    }

    private void syncTaskMonitor(Throwable ex, String message, Object... ars) {
        log.error(message, ars, ex);
        syncProgressSenders.forEach(sender -> {
            try {
                sender.send(MessageFormatter.arrayFormat(message, ars).getMessage() + "\n"
                        + ExceptionUtils.getRootCauseMessage(ex) + "\n");
            }
            catch (Exception e) {
                log.warn("{} send blog sync monitor info error", sender.getClass().getName(), e);
            }
        });
    }

}
