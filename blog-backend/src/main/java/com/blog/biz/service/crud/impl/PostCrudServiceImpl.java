package com.blog.biz.service.crud.impl;

import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.repository.PostRepository;
import com.blog.biz.service.crud.PostCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class PostCrudServiceImpl extends BaseCrudServiceImpl<PostEntity, PostRepository> implements PostCrudService {
    public PostCrudServiceImpl(PostRepository repository) {
        super(repository);
    }
}
