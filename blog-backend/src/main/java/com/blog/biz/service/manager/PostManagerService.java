package com.blog.biz.service.manager;

import com.blog.biz.model.request.SearchPostRequest;
import com.blog.biz.model.request.blog.SearchPostBlogRequest;
import com.blog.biz.model.response.PostResponse;
import com.blog.biz.model.response.blog.PostBlogResponse;
import com.blog.common.base.response.PageResponse;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface PostManagerService {

    /**
     * 查询文章列表
     *
     * @param request PagePostRequest
     * @return PageResponse<PagePostResponse>
     */
    PageResponse<PostResponse> adminSearch(SearchPostRequest request);

    /**
     * 博客页面查询文章列表
     *
     * @param request
     * @return PageResponse<PostBlogResponse>
     **/
    PageResponse<PostBlogResponse> blogSearch(SearchPostBlogRequest request);

    /**
     * 发布文章
     *
     * @param postId Long
     */
    void publish(Long postId);

    /**
     * 下架文章
     *
     * @param postId Long
     */
    void remove(Long postId);

    /**
     * 查询文章内容
     *
     * @param postId
     * @return String
     **/
    String getPostContent(Long postId);
}
