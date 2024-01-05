package com.blog.biz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.biz.model.entity.PostTagRelaEntity;

/**
 * @author zouzhangpeng
 * @desc
 */
@Repository
public interface PostTagRelaRepository extends JpaRepository<PostTagRelaEntity,Long> {
}
