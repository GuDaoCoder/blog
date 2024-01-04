package com.blog.biz.service.manager;

import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.PageTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
import com.blog.biz.model.response.CreateTagResponse;
import com.blog.biz.model.response.TagResponse;
import com.blog.common.base.response.PageResponse;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface TagManagerService {

    /**
     * 新增标签
     * 
     * @param request CreateTagRequest
     * @return com.blog.biz.model.response.CreateTagResponse
     */
    CreateTagResponse add(CreateTagRequest request);

    /**
     * 编辑标签
     * 
     * @param tagId Long
     * @param request UpdateTagRequest
     */
    void update(Long tagId, UpdateTagRequest request);

    /**
     * 分页查询标签
     * 
     * @param request PageTagRequest
     * @return com.blog.common.base.response.PageResponse<com.blog.biz.model.response.TagResponse>
     */
    PageResponse<TagResponse> page(PageTagRequest request);

    /**
     * 向上移动标签顺序
     * 
     * @param tagId Long
     */
    void moveUp(Long tagId);

    /**
     * 向下移动标签顺序
     * 
     * @param tagId Long
     */
    void moveDown(Long tagId);
}
