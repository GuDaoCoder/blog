package com.blog.biz.service.crud;

import com.blog.biz.model.entity.CategoryEntity;
import com.blog.common.base.service.IBaseCrudService;

import java.util.List;
import java.util.Optional;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface CategoryCrudService extends IBaseCrudService<CategoryEntity> {

    /**
     * 根据名称查询分类
     * 
     * @param categoryName String
     * @return java.util.Optional<com.blog.biz.model.entity.CategoryEntity>
     */
    Optional<CategoryEntity> findByCategoryName(String categoryName);

    /**
     * 查询最新的文章分类，如果存在上级，则查询上级分类下最新的
     * 
     * @param parentId Long
     * @return java.util.Optional<com.blog.biz.model.entity.CategoryEntity>
     */
    Optional<CategoryEntity> findLatest(Long parentId);

    List<CategoryEntity> findAllByCondition(String categoryName,Boolean enabled);
}
