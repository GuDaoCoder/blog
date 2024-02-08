package com.blog.biz.controller.admin;

import com.blog.biz.model.request.CategoryTreeRequest;
import com.blog.biz.model.request.UpdateCategoryRequest;
import com.blog.biz.model.response.CategoryTreeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.response.CreateCategoryResponse;
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
@Tag(name = "分类管理")
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryManagerService categoryManagerService;

    @Operation(summary = "查询分类树形结构")
    @GetMapping
    public Result<List<CategoryTreeResponse>> tree(CategoryTreeRequest request) {
        return Result.success(categoryManagerService.tree(request));
    }

    @Operation(summary = "新增分类")
    @PostMapping
    public Result<CreateCategoryResponse> create(@Validated @RequestBody CreateCategoryRequest request) {
        return Result.success(categoryManagerService.create(request));
    }

    @Operation(summary = "编辑分类")
    @PutMapping("/{categoryId}")
    public Result<Void> update(@Parameter(description = "分类Id") @PathVariable Long categoryId,
                               @Validated @RequestBody UpdateCategoryRequest request) {
        categoryManagerService.update(categoryId, request);
        return Result.success();
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{categoryId}")
    public Result<Void> delete(@Parameter(description = "分类Id") @PathVariable Long categoryId) {
        categoryManagerService.delete(categoryId);
        return Result.success();
    }
}
