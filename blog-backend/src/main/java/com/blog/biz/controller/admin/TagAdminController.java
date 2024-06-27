package com.blog.biz.controller.admin;

import com.blog.biz.model.request.TagSearchRequest;
import com.blog.biz.model.response.TagDetailResponse;
import com.blog.biz.service.manager.TagManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouzhangpeng
 * @desc
 */
@Tag(name = "标签管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/tags")
public class TagAdminController {

    private final TagManagerService tagManagerService;

    @Operation(summary = "查询标签列表")
    @GetMapping
    public Result<PageResponse<TagDetailResponse>> search(@ParameterObject TagSearchRequest request) {
        return Result.success(tagManagerService.search(request));
    }

}
