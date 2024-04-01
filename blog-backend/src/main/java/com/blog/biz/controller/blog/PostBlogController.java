package com.blog.biz.controller.blog;

import com.blog.biz.model.request.blog.SearchPostBlogRequest;
import com.blog.biz.model.response.blog.PostBlogResponse;
import com.blog.biz.service.manager.PostManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "文章管理")
@RestController
@RequestMapping("/blog/posts")
public class PostBlogController {

    private final PostManagerService postManagerService;

    @Operation(summary = "查询文章列表")
    @GetMapping
    public Result<PageResponse<PostBlogResponse>> search(@ParameterObject @Validated SearchPostBlogRequest request) {
        return Result.success(postManagerService.blogSearch(request));
    }

    @Operation(summary = "查询文章内容")
    @GetMapping("/{postId}/content")
    public Result<String> content(@Parameter(description = "文章Id") @PathVariable Long postId) {
        return Result.success(postManagerService.getPostContent(postId));
    }
}
