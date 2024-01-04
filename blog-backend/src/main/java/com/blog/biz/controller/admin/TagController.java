package com.blog.biz.controller.admin;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.PageTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
import com.blog.biz.model.response.CreateTagResponse;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.service.manager.TagManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.domain.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Tag(name = "标签管理")
@RestController
@RequestMapping("/admin/tag")
public class TagController {

    private final TagManagerService tagManagerService;

    @Operation(summary = "新增标签")
    @PostMapping
    public Result<CreateTagResponse> add(@RequestBody CreateTagRequest request) {
        return Result.success(tagManagerService.add(request));
    }

    @Operation(summary = "编辑标签")
    @PutMapping("/{tagId}")
    public Result<Void> add(@Parameter(description = "标签Id") @PathVariable Long tagId,
        @RequestBody UpdateTagRequest request) {
        tagManagerService.update(tagId, request);
        return Result.success();
    }

    @Operation(summary = "查询标签列表")
    @GetMapping
    public Result<PageResponse<TagResponse>> page(@ParameterObject PageTagRequest request) {
        return Result.success(tagManagerService.page(request));
    }

    @Operation(summary = "向上移动标签顺序")
    @PutMapping("/{tagId}/moveUp")
    public Result<Void> moveUp(@Parameter(description = "标签Id") @PathVariable Long tagId) {
        tagManagerService.moveUp(tagId);
        return Result.success();
    }

    @Operation(summary = "向下移动标签顺序")
    @PutMapping("/{tagId}/moveDown")
    public Result<Void> moveDown(@Parameter(description = "标签Id") @PathVariable Long tagId) {
        tagManagerService.moveDown(tagId);
        return Result.success();
    }
}
