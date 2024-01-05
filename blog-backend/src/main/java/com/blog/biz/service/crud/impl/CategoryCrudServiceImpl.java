package com.blog.biz.service.crud.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.model.entity.QCategoryEntity;
import com.blog.biz.repository.CategoryRepository;
import com.blog.biz.service.crud.CategoryCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import com.blog.common.util.JpaUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class CategoryCrudServiceImpl extends BaseCrudServiceImpl<CategoryEntity, CategoryRepository>
    implements CategoryCrudService {
    public CategoryCrudServiceImpl(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Optional<CategoryEntity> findByCategoryName(String categoryName) {
        return JpaUtil.query(QCategoryEntity.categoryEntity)
            .eq(QCategoryEntity.categoryEntity.categoryName, categoryName).fetchOne();
    }

    @Override
    public Optional<CategoryEntity> findLatest(Long parentId) {
        return JpaUtil.query(QCategoryEntity.categoryEntity).eq(QCategoryEntity.categoryEntity.parentId, parentId)
            .order(QCategoryEntity.categoryEntity.orderNo.desc()).limit(1L).fetchOne();
    }
}
