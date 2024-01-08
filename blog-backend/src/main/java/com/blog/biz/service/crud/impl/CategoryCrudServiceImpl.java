package com.blog.biz.service.crud.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.biz.mapper.CategoryMapper;
import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.service.crud.CategoryCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class CategoryCrudServiceImpl extends BaseCrudServiceImpl<CategoryMapper, CategoryEntity>
    implements CategoryCrudService {

    @Override
    public Optional<CategoryEntity> findByCategoryName(String categoryName) {
        LambdaQueryWrapper<CategoryEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CategoryEntity::getCategoryName, categoryName);
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }

    @Override
    public Optional<CategoryEntity> findLatest(Long parentId) {
        LambdaQueryWrapper<CategoryEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CategoryEntity::getParentId, parentId).orderByDesc(CategoryEntity::getOrderNo);
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }
}
