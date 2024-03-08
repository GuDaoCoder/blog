package com.blog.biz.service.crud;

import com.blog.biz.model.entity.PostContentEntity;
import com.blog.common.base.service.IBaseCrudService;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface PostContentCrudService extends IBaseCrudService<PostContentEntity> {

    /**
     * 更新文章内容
     *
     * @param postId
     * @param content
     * @return void
     **/
    void updateContentByPostId(Long postId, String content);

    /**
     * 保存文章内容
     *
     * @param postId
     * @param content
     * @return Long
     **/
    Long saveContent(Long postId, String content);
}
