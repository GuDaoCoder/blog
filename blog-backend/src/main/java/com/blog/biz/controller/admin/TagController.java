package com.blog.biz.controller.admin;

import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.SearchTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
import com.blog.biz.model.response.TagResponse;
import com.blog.biz.service.manager.TagManagerService;
import com.blog.common.base.response.SearchResponse;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "查询标签列表")
    @GetMapping
    public Result<SearchResponse<TagResponse>> search(@ParameterObject SearchTagRequest request) {
        return Result.success(tagManagerService.search(request));
    }

    @Operation(summary = "新增标签")
    @PostMapping
    public Result<TagResponse> create(@Validated @RequestBody CreateTagRequest request) {
        return Result.success(tagManagerService.create(request));
    }

    @Operation(summary = "编辑标签")
    @PatchMapping("/{tagId}")
    public Result<TagResponse> update(@Parameter(description = "标签Id") @PathVariable Long tagId,
                                      @Validated @RequestBody UpdateTagRequest request) {
        return Result.success(tagManagerService.update(tagId, request));
    }

    @Operation(summary = "删除标签")
    @DeleteMapping("/{tagId}")
    public Result<Void> delete(@Parameter(description = "标签Id") @PathVariable Long tagId) {
        tagManagerService.delete(tagId);
        return Result.success();
    }
}
