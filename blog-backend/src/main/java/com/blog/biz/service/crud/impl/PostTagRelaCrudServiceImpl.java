package com.blog.biz.service.crud.impl;

import com.blog.biz.model.entity.PostTagRelaEntity;
import com.blog.biz.repository.PostTagRelaRepository;
import com.blog.biz.service.crud.PostTagRelaCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class PostTagRelaCrudServiceImpl extends BaseCrudServiceImpl<PostTagRelaEntity, PostTagRelaRepository>
    implements PostTagRelaCrudService {
    public PostTagRelaCrudServiceImpl(PostTagRelaRepository repository) {
        super(repository);
    }
}
