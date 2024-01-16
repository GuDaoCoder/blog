package com.blog.biz.service.crud.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import com.blog.biz.mapper.PostTagRelaMapper;
import com.blog.biz.model.entity.PostTagRelaEntity;
import com.blog.biz.service.crud.PostTagRelaCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class PostTagRelaCrudServiceImpl extends BaseCrudServiceImpl<PostTagRelaMapper, PostTagRelaEntity>
    implements PostTagRelaCrudService {
    @Override
    public boolean tagUsed(Long tagId) {
        LambdaQueryWrapper<PostTagRelaEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PostTagRelaEntity::getTagId, tagId);
        return baseMapper.selectCount(queryWrapper) > 0;
    }
}
