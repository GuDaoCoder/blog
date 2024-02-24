package com.blog.biz.service.crud.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.biz.mapper.TagMapper;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.service.crud.TagCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class TagCrudServiceImpl extends BaseCrudServiceImpl<TagMapper, TagEntity> implements TagCrudService {

    @Override
    public Optional<TagEntity> findByTagName(String tagName) {
        LambdaQueryWrapper<TagEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TagEntity::getTagName, tagName);
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }

    @Override
    public IPage<TagEntity> page(String tagName, IPage<TagEntity> pageable) {
        LambdaQueryWrapper<TagEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(tagName), TagEntity::getTagName, tagName)
                .orderByDesc(TagEntity::getUpdateTime);
        return baseMapper.selectPage(pageable, queryWrapper);
    }

    @Override
    public List<TagEntity> findAllByTagNames(List<String> tagNames) {
        if (CollectionUtils.isEmpty(tagNames)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<TagEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(TagEntity::getTagName, tagNames);
        return baseMapper.selectList(queryWrapper);
    }

}
