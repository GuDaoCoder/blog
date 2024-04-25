package com.blog.biz.service.manager.impl;

import com.blog.biz.convert.GitRepositoryConverter;
import com.blog.biz.model.entity.GitRepositoryEntity;
import com.blog.biz.model.request.GitRepositoryRequest;
import com.blog.biz.model.response.GitRepositoryResponse;
import com.blog.biz.service.crud.GitRepositoryCrudService;
import com.blog.biz.service.manager.GitRepositoryManagerService;
import com.blog.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class GitRepositoryManagerServiceImpl implements GitRepositoryManagerService {

    private final GitRepositoryCrudService gitRepositoryCrudService;

    @Override
    public List<GitRepositoryResponse> list() {
        return gitRepositoryCrudService.list().stream().map(GitRepositoryConverter.INSTANCE::toResponse).toList();
    }

    @Override
    public Long save(GitRepositoryRequest request) {
        if (Objects.isNull(request.getGitRepositoryId()) && gitRepositoryCrudService.count() > 0) {
            throw new BusinessException("Git仓库信息已存在");
        }
        GitRepositoryEntity gitRepositoryEntity = GitRepositoryConverter.INSTANCE.toEntity(request);
        gitRepositoryCrudService.saveOrUpdate(gitRepositoryEntity);

        return gitRepositoryEntity.getGitRepositoryId();
    }
}
