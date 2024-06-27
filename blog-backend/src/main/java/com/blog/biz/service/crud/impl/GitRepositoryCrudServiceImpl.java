package com.blog.biz.service.crud.impl;

import com.blog.biz.mapper.GitRepositoryMapper;
import com.blog.biz.model.entity.GitRepositoryEntity;
import com.blog.biz.service.crud.GitRepositoryCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GitRepositoryCrudServiceImpl extends BaseCrudServiceImpl<GitRepositoryMapper, GitRepositoryEntity>
		implements GitRepositoryCrudService {

}
