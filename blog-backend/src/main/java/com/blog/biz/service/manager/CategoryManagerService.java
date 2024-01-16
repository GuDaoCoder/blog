package com.blog.biz.service.manager;

import java.util.List;

import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.request.UpdateCategoryRequest;
import com.blog.biz.model.response.CategoryResponse;
import com.blog.biz.model.response.CreateCategoryResponse;
import com.blog.common.base.response.NodeResponse;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface CategoryManagerService {

    /**
     * 新增分类
     *
     * @param request CreateCategoryRequest
     * @return com.blog.biz.model.response.CreateCategoryResponse
     */
    CreateCategoryResponse create(CreateCategoryRequest request);

    /**
     * 查询分类树形结构
     *
     * @param
     * @return java.util.List<com.blog.common.base.response.NodeResponse<com.blog.biz.model.response.CategoryResponse>>
     */
    List<NodeResponse<CategoryResponse>> tree();

    /**
     * 更新分类
     * 
     * @param categoryId Long
     * @param request UpdateCategoryRequest
     */
    void update(Long categoryId, UpdateCategoryRequest request);

    /**
     * 删除分类
     *
     * @param categoryId Long
     */
    void delete(Long categoryId);
}
