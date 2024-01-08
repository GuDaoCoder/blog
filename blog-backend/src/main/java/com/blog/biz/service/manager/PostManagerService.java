package com.blog.biz.service.manager;

import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.request.PagePostRequest;
import com.blog.biz.model.response.CreatePostResponse;
import com.blog.biz.model.response.PagePostResponse;
import com.blog.common.base.response.PageResponse;

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
     * 查询文章列表
     * 
     * @param request PagePostRequest
     * @return PageResponse<PagePostResponse>
     */
    PageResponse<PagePostResponse> page(PagePostRequest request);

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
