package com.blog.biz.service.crud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.model.context.PagePostContext;
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
     * @param context PagePostContext
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.blog.biz.model.entity.PostEntity>
     */
    IPage<PostEntity> page(PagePostContext context);

    /**
     *
     * 查询分类是否被使用
     * 
     * @param categoryId Long
     * @return boolean
     */
    boolean categoryUsed(Long categoryId);

}
