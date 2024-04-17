package com.blog.biz.service.crud.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.biz.mapper.CategoryMapper;
import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.service.crud.CategoryCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        queryWrapper.eq(CategoryEntity::getParentCategoryId, parentId).orderByDesc(CategoryEntity::getOrderNo)
                .last("limit 1");
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }

    @Override
    public List<String> findAllByCategoryNames(List<String> categoryNames) {
        if (CollectionUtils.isEmpty(categoryNames)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<CategoryEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(CategoryEntity::getCategoryName, categoryNames);
        return baseMapper.selectList(queryWrapper).stream().map(CategoryEntity::getCategoryName).collect(Collectors.toList());
    }

    @Override
    public List<CategoryEntity> findAll() {
        return list();
    }

    @Override
    public List<CategoryEntity> findAllByCondition(String categoryName) {
        LambdaQueryWrapper<CategoryEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(categoryName), CategoryEntity::getCategoryName, categoryName);
        return baseMapper.selectList(queryWrapper);
    }
}
