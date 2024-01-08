package com.blog.biz.service.crud.impl;

import com.blog.biz.model.entity.PostContentEntity;
import com.blog.biz.repository.PostContentRepository;
import com.blog.biz.service.crud.PostContentCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class PostContentCrudServiceImpl extends BaseCrudServiceImpl<PostContentEntity, PostContentRepository> implements PostContentCrudService {
    public PostContentCrudServiceImpl(PostContentRepository repository) {
        super(repository);
    }
}
