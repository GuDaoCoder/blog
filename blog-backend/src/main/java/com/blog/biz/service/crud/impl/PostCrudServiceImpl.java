package com.blog.biz.service.crud.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.biz.mapper.PostMapper;
import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.service.crud.PostCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class PostCrudServiceImpl extends BaseCrudServiceImpl<PostMapper, PostEntity> implements PostCrudService {

    @Override
    public IPage<PostEntity> page(PostEntity postEntity, Boolean encrypt, IPage<PostEntity> page) {
        LambdaQueryWrapper<PostEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(postEntity.getTitle()), PostEntity::getTitle, postEntity.getTitle())
            .eq(postEntity.getCategoryId() != null, PostEntity::getCategoryId, postEntity.getCategoryId())
            .isNotNull(encrypt, PostEntity::getPassword).orderByDesc(PostEntity::getCreateTime);
        return baseMapper.selectPage(page, queryWrapper);
    }

}
