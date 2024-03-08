package com.blog.biz.service.manager;

import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.request.SearchPostRequest;
import com.blog.biz.model.request.UpdatePostRequest;
import com.blog.biz.model.response.PostResponse;
import com.blog.common.base.response.SearchResponse;

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
    SearchResponse<PostResponse> search(SearchPostRequest request);

    /**
     * 创建文章
     *
     * @param request CreatePostRequest
     * @return com.blog.biz.model.response.CreatePostResponse
     */
    PostResponse create(CreatePostRequest request);

    /**
     * 更新文章
     *
     * @param postId
     * @param request
     * @return PostResponse
     **/
    PostResponse update(Long postId, UpdatePostRequest request);

    /**
     * 删除文章
     *
     * @param postId Long
     */
    void delete(Long postId);


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
