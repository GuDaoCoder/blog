package com.blog.biz.service.manager;

import com.blog.biz.model.request.PostPortalSearchRequest;
import com.blog.biz.model.request.PostSearchRequest;
import com.blog.biz.model.response.PostDetailResponse;
import com.blog.biz.model.response.PostResponse;
import com.blog.common.base.response.PageResponse;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface PostManagerService {

	/**
	 * 查询文章列表
	 * @param request PagePostRequest
	 * @return PageResponse<PagePostResponse>
	 */
	PageResponse<PostResponse> search(PostSearchRequest request);

	/**
	 * 博客页面查询文章列表
	 * @param request
	 * @return PageResponse<PostResponse>
	 **/
	PageResponse<PostResponse> blogSearch(PostPortalSearchRequest request);

	/**
	 * 查询文章详情
	 * @param postId
	 * @return PostDetailResponse
	 **/
	PostDetailResponse detail(Long postId);

	/**
	 * 发布文章
	 * @param postId Long
	 */
	void publish(Long postId);

	/**
	 * 下架文章
	 * @param postId Long
	 */
	void remove(Long postId);

	/**
	 * 查询文章内容
	 * @param postId
	 * @return String
	 **/
	String getContent(Long postId);

	/**
	 * 设置封面图片
	 * @param postId
	 * @param coverPictureUrl
	 * @return void
	 **/
	void updateCoverPicture(Long postId, String coverPictureUrl);

}
