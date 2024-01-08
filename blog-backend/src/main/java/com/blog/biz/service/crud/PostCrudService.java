package com.blog.biz.service.crud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.model.entity.PostEntity;
import com.blog.common.base.service.IBaseCrudService;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface PostCrudService extends IBaseCrudService<PostEntity> {

    /**
     * 分页查询文章
     * 
     * @param postEntity PostEntity
     * @param encrypt Boolean
     * @param page PostEntity>
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.blog.biz.model.entity.PostEntity>
     */
    IPage<PostEntity> page(PostEntity postEntity, Boolean encrypt, IPage<PostEntity> page);
}
