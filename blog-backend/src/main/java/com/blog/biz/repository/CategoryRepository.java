package com.blog.biz.repository;

import com.blog.biz.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zouzhangpeng
 * @desc
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {}
