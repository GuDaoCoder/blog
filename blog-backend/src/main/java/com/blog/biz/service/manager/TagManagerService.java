package com.blog.biz.service.manager;

import com.blog.biz.model.request.SearchTagRequest;
import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
import com.blog.biz.model.response.TagResponse;
import com.blog.common.base.response.SearchResponse;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface TagManagerService {

    /**
     * 查询标签
     *
     * @param request
     * @return SearchResponse<TagResponse>
     **/
    SearchResponse<TagResponse> search(SearchTagRequest request);

    /**
     * 新增标签
     *
     * @param request
     * @return TagResponse
     **/
    TagResponse create(CreateTagRequest request);

    /**
     * 编辑标签
     *
     * @param tagId
     * @param request
     * @return TagResponse
     **/
    TagResponse update(Long tagId, UpdateTagRequest request);


    /**
     * 删除标签
     *
     * @param tagId Long
     */
    void delete(Long tagId);
}
