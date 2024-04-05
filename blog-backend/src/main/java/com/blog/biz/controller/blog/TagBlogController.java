package com.blog.biz.controller.blog;

import com.blog.biz.model.request.SearchTagRequest;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.service.manager.TagManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "标签管理")
@RestController
@RequestMapping("/blog/tags")
public class TagBlogController {

    private final TagManagerService tagManagerService;

    @Operation(summary = "查询标签列表")
    @GetMapping
    public Result<PageResponse<TagResponse>> search(@ParameterObject SearchTagRequest request) {
        return Result.success(tagManagerService.search(request));
    }
}
