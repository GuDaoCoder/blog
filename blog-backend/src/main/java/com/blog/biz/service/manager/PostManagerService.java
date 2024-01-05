package com.blog.biz.service.manager;

import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.response.CreatePostResponse;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface PostManagerService {

    /**
     * 创建文章
     * 
     * @param request CreatePostRequest
     * @return com.blog.biz.model.response.CreatePostResponse
     */
    CreatePostResponse create(CreatePostRequest request);

    /**
     * 发布文章
     * 
     * @param postId Long
     */
    void publish(Long postId);

    /**
     * 取消发布文章
     * 
     * @param postId Long
     */
    void unpublish(Long postId);
}
