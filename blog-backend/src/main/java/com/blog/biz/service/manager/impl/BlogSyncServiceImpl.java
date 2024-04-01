package com.blog.biz.service.manager.impl;

import cn.hutool.core.io.FileUtil;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.model.entity.PostContentEntity;
import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.service.crud.CategoryCrudService;
import com.blog.biz.service.crud.PostContentCrudService;
import com.blog.biz.service.crud.PostCrudService;
import com.blog.biz.service.manager.BlogSyncService;
import com.blog.common.properties.GitProjectProperties;
import com.blog.common.util.MarkdownUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BlogSyncServiceImpl implements BlogSyncService {

    private final GitProjectProperties gitProjectProperties;

    private final CategoryCrudService categoryCrudService;

    private final PostCrudService postCrudService;

    private final PostContentCrudService postContentCrudService;

    @SneakyThrows
    private void pullProject() {
        Git git = null;

        try {
            git = git();
            checkout(git);
            git.pull().call();
        } finally {
            if (git != null) {
                git.close();
            }
        }

    }

    @SneakyThrows
    private void checkout(Git git) {
        // 如果分支在本地已存在，直接checkout
        if (existBranch(git)) {
            git.checkout().setCreateBranch(false).setName(gitProjectProperties.getBranch()).call();
        } else {
            // 如果分支在本地不存在，需要从远程分支上面checkout。
            git.checkout().setCreateBranch(true).setName(gitProjectProperties.getBranch()).setStartPoint("origin/" + gitProjectProperties.getBranch()).call();
        }
    }


    @SneakyThrows
    public boolean existBranch(Git git) {
        List<Ref> refs = git.branchList().call();
        for (Ref ref : refs) {
            if (ref.getName().contains(gitProjectProperties.getBranch())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取git对象，打开已有git仓库或者clone git项目
     *
     * @param
     * @return Git
     **/
    private Git git() {
        Git git;
        try {
            git = Git.open(new File(gitProjectProperties.getLocalRepositoryPath() + File.separator + ".git"));
        } catch (IOException e) {
            return cloneProject();
        }
        return git;
    }

    @SneakyThrows
    private Git cloneProject() {
        CloneCommand cloneCommand = Git.cloneRepository().setURI(gitProjectProperties.getGitRepositoryUrl()).setDirectory(new File(gitProjectProperties.getLocalRepositoryPath()));
        if (StringUtils.isNoneBlank(gitProjectProperties.getUsername(), gitProjectProperties.getPassword())) {
            cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitProjectProperties.getUsername(), gitProjectProperties.getPassword()));
        }
        return cloneCommand.call();
    }

    @Override
    public void sync() {
        pullProject();

        List<File> files = FileUtil.loopFiles(gitProjectProperties.getLocalRepositoryPath(), file -> file.getName().endsWith(".md"));
        if (CollectionUtils.isEmpty(files)) {
            return;
        }

        // 查询所有文章分类
        List<CategoryEntity> allCategoryEntities = categoryCrudService.findAll();

        for (File file : files) {
            // 同步文章分类
            CategoryEntity categoryEntity = syncCategory(file, allCategoryEntities);
            if (categoryEntity == null) {
                continue;
            }
            // 同步文章
            syncPost(file, categoryEntity);
        }

    }

    /**
     * 同步文章分类
     *
     * @param file
     * @param allCategoryEntities
     * @return CategoryEntity
     **/
    private CategoryEntity syncCategory(File file, List<CategoryEntity> allCategoryEntities) {
        Path relativizePath = Paths.get(gitProjectProperties.getLocalRepositoryPath()).relativize(Paths.get(file.getAbsolutePath()));
        CategoryEntity parentCategoryEntity = null;
        // -1是因为不包括文件名
        int count = relativizePath.getNameCount() - 1;
        if (count <= 0) {
            return null;
        }
        for (int level = 0; level < count; level++) {
            int finalLevel = level;
            String folderName = relativizePath.getName(finalLevel).toString();
            String categoryName = getCategoryName(folderName);
            CategoryEntity findCategoryEntity = allCategoryEntities.stream().filter(entity -> StringUtils.equals(entity.getCategoryName(), categoryName) && finalLevel == entity.getLevel()).findFirst().orElse(null);
            if (findCategoryEntity == null) {
                findCategoryEntity = new CategoryEntity();
                findCategoryEntity.setParentCategoryId(Objects.isNull(parentCategoryEntity) ? 0L : parentCategoryEntity.getCategoryId()).setCategoryName(categoryName).setFullId(Objects.isNull(parentCategoryEntity) ? "0" : (parentCategoryEntity.getFullId() + parentCategoryEntity.getCategoryId())).setOrderNo(getCategoryOrder(folderName)).setLevel(level).setEnabled(Boolean.TRUE);
                categoryCrudService.save(findCategoryEntity);
                allCategoryEntities.add(findCategoryEntity);
            }
            parentCategoryEntity = findCategoryEntity;
        }
        return parentCategoryEntity;
    }

    /**
     * 同步文章基本信息
     *
     * @param file
     * @param categoryEntity
     * @return void
     **/
    private void syncPost(File file, CategoryEntity categoryEntity) {
        String title = getPostName(file.getName());
        PostEntity postEntity = postCrudService.findByTitle(title).orElse(null);

        // 是否需要新增
        boolean add = Objects.isNull(postEntity);
        // 是否需要更新
        boolean update = false;

        PostContentEntity postContentEntity = add ? new PostContentEntity()
                : postContentCrudService.getByField(PostContentEntity::getPostId, postEntity.getPostId()).orElse(new PostContentEntity());

        LocalDateTime fileLastUpdate = getFileLastUpdate(file);
        String content = FileUtil.readString(file, StandardCharsets.UTF_8);
        if (add) {
            postEntity = new PostEntity();
            postEntity.setTitle(title)
                    .setSource(PostSource.MD_SYNC)
                    .setStatus(PostStatus.DRAFT)
                    .setEnableComment(Boolean.TRUE)
                    .setTop(Boolean.FALSE)
                    .setCategoryId(categoryEntity.getCategoryId())
                    .setCoverPictureUrl("https://api.isoyu.com/bing_images.php?t=" + LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond())
                    .setFileLastUpdateTime(fileLastUpdate);
            if (StringUtils.isNotBlank(content)) {
                postEntity.setSummary(MarkdownUtil.parseSummary(Parser.builder().build().parse(content)));
            }
            postCrudService.save(postEntity);
        } else {
            if (fileLastUpdate.isAfter(postEntity.getFileLastUpdateTime())) {
                update = true;
            }
        }
        if (add || update) {
            postContentEntity.setPostId(postEntity.getPostId())
                    .setContent(content);
            postContentCrudService.saveOrUpdate(postContentEntity);
        }
    }

    /**
     * 获取文章分类顺序
     *
     * @param foldName
     * @return int
     **/
    private int getCategoryOrder(String foldName) {
        int firstDashIndex = foldName.indexOf("-");
        if (firstDashIndex != -1) {
            try {
                return Integer.parseInt(foldName.substring(0, firstDashIndex));
            } catch (Exception e) {
                return 999;
            }
        }
        return 999;
    }

    /**
     * 获取文章分类名称
     *
     * @param foldName
     * @return String
     **/
    private String getCategoryName(String foldName) {
        int firstDashIndex = foldName.indexOf("-");
        if (firstDashIndex != -1) {
            return foldName.substring(firstDashIndex + 1);
        }
        return foldName;
    }

    /**
     * 获取文章名称
     *
     * @param fileName
     * @return String
     **/
    private String getPostName(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    /**
     * 获取文件的最后更新时间
     *
     * @param file
     * @return LocalDateTime
     **/
    private LocalDateTime getFileLastUpdate(File file) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
    }

}
