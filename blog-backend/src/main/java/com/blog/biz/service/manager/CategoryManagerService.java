package com.blog.biz.service.manager;

import com.blog.biz.model.request.CategoryAdminTreeRequest;
import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.request.UpdateCategoryRequest;
import com.blog.biz.model.response.CategoryResponse;
import com.blog.biz.model.response.CategoryTreeResponse;

import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface CategoryManagerService {

	/**
	 * 查询文章分类树
	 * @param request
	 * @return List<TreeCategoryResponse>
	 **/
	List<CategoryTreeResponse> tree(CategoryAdminTreeRequest request);

	/**
	 * 新增分类
	 * @param request
	 * @return CategoryResponse
	 **/
	CategoryResponse create(CreateCategoryRequest request);

	/**
	 * 更新分类
	 * @param categoryId
	 * @param request
	 * @return CategoryResponse
	 **/
	CategoryResponse update(Long categoryId, UpdateCategoryRequest request);

	/**
	 * 删除分类
	 * @param categoryId Long
	 */
	void delete(Long categoryId);

}
