package com.blog.biz.service.manager;

import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.SearchTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
import com.blog.biz.model.response.TagDetailResponse;
import com.blog.biz.model.response.TagResponse;
import com.blog.common.base.response.PageResponse;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface TagManagerService {

    /**
     * 查询标签
     *
     * @param request
     * @return PageResponse<TagDetailResponse>
     **/
    PageResponse<TagDetailResponse> search(SearchTagRequest request);
}
