package com.blog.biz.service.manager;

import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.request.PagePostRequest;
import com.blog.biz.model.request.UpdatePostRequest;
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
     * 更新文章
     *
     * @param postId Long
     * @param request UpdatePostRequest
     */
    void update(Long postId, UpdatePostRequest request);

    /**
     * 删除文章
     * 
     * @param postId Long
     */
    void delete(Long postId);

    /**
     * 查询文章列表
     * 
     * @param request PagePostRequest
     * @return PageResponse<PagePostResponse>
     */
    PageResponse<PagePostResponse> page(PagePostRequest request);

    /**
     * 移动至回收站
     * 
     * @param postId Long
     */
    void moveRecycleBin(Long postId);

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
    void unpublished(Long postId);
}
