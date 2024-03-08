package com.blog.biz.service.manager;

import java.util.List;

import com.blog.biz.model.request.SearchCategoryTreeRequest;
import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.request.UpdateCategoryRequest;
import com.blog.biz.model.response.CategoryTreeResponse;
import com.blog.biz.model.response.CategoryResponse;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface CategoryManagerService {

    /**
     * 查询文章分类树
     *
     * @param request
     * @return List<TreeCategoryResponse>
     **/
    List<CategoryTreeResponse> searchTree(SearchCategoryTreeRequest request);

    /**
     * 新增分类
     *
     * @param request
     * @return CategoryResponse
     **/
    CategoryResponse create(CreateCategoryRequest request);

    /**
     * 更新分类
     *
     * @param categoryId
     * @param request
     * @return CategoryResponse
     **/
    CategoryResponse update(Long categoryId, UpdateCategoryRequest request);

    /**
     * 删除分类
     *
     * @param categoryId Long
     */
    void delete(Long categoryId);
}
