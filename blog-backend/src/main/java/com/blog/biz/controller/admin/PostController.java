package com.blog.biz.controller.admin;

import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.response.CreatePostResponse;
import com.blog.biz.service.manager.PostManagerService;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Tag(name = "文章管理")
@RestController
@RequestMapping("/admin/post")
public class PostController {

    private final PostManagerService postManagerService;

    @Operation(summary = "新增文章")
    @PostMapping
    public Result<CreatePostResponse> create(@RequestBody CreatePostRequest request) {
        return Result.success(postManagerService.create(request));
    }
}
