package com.blog.biz.service.crud;

import org.springframework.data.domain.Page;

import com.blog.biz.model.entity.PostEntity;
import com.blog.common.base.service.BaseCrudService;
import org.springframework.data.domain.Pageable;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface PostCrudService extends BaseCrudService<PostEntity> {

    /**
     * 分页查询文章
     * 
     * @param postEntity PostEntity
     * @param encrypt Boolean
     * @param pageable Pageable
     * @return org.springframework.data.domain.Page<com.blog.biz.model.entity.PostEntity>
     */
    Page<PostEntity> page(PostEntity postEntity, Boolean encrypt, Pageable pageable);
}
