package com.blog.biz.repository;

import com.blog.biz.model.entity.PostContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zouzhangpeng
 * @desc
 */
@Repository
public interface PostContentRepository extends JpaRepository<PostContentEntity, Long> {}
