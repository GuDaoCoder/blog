package com.blog.biz.service.crud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.model.context.SearchPostContext;
import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.model.entity.custom.CategoryPostCountEntity;
import com.blog.common.base.service.IBaseCrudService;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
    IPage<PostEntity> page(SearchPostContext context);

    /**
     * 查询分类是否被使用
     *
     * @param categoryId Long
     * @return boolean
     */
    boolean categoryUsed(Long categoryId);

    /**
     * 查询分类对应文章数量
     *
     * @param categoryIds
     * @return List<CategoryPostCountEntity>
     **/
    List<CategoryPostCountEntity> getCategoryPostCountEntity(Collection<? extends Serializable> categoryIds);
}
