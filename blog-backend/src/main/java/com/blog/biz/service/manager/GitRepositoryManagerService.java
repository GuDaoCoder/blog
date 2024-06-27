package com.blog.biz.service.manager;

import com.blog.biz.model.request.GitRepositoryRequest;
import com.blog.biz.model.response.GitRepositoryResponse;

import java.util.List;

public interface GitRepositoryManagerService {

    List<GitRepositoryResponse> list();

    Long save(GitRepositoryRequest request);

}
