package com.blog.biz.controller.admin;

import com.blog.biz.model.request.SearchCategoryTreeRequest;
import com.blog.biz.model.request.UpdateCategoryRequest;
import com.blog.biz.model.response.CategoryTreeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.response.CategoryResponse;
import com.blog.biz.service.manager.CategoryManagerService;
import com.blog.common.domain.Result;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Tag(name = "文章分类管理")
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryManagerService categoryManagerService;

    @Operation(summary = "查询文章分类树")
    @GetMapping
    public Result<List<CategoryTreeResponse>> searchTree(@ParameterObject SearchCategoryTreeRequest request) {
        return Result.success(categoryManagerService.searchTree(request));
    }

    @Operation(summary = "新增分类")
    @PostMapping
    public Result<CategoryResponse> create(@Validated @RequestBody CreateCategoryRequest request) {
        return Result.success(categoryManagerService.create(request));
    }

    @Operation(summary = "编辑分类")
    @PatchMapping("/{categoryId}")
    public Result<CategoryResponse> update(@Parameter(description = "分类Id") @PathVariable Long categoryId,
                                           @Validated @RequestBody UpdateCategoryRequest request) {
        return Result.success(categoryManagerService.update(categoryId, request));
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{categoryId}")
    public Result<Void> delete(@Parameter(description = "分类Id") @PathVariable Long categoryId) {
        categoryManagerService.delete(categoryId);
        return Result.success();
    }
}
