package com.blog.biz.service.crud.impl;

import org.springframework.stereotype.Service;

import com.blog.biz.mapper.PostContentMapper;
import com.blog.biz.model.entity.PostContentEntity;
import com.blog.biz.service.crud.PostContentCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class PostContentCrudServiceImpl extends BaseCrudServiceImpl<PostContentMapper, PostContentEntity> implements PostContentCrudService {
}
