package com.blog.biz.controller.portal;

import com.blog.biz.model.request.CategoryAdminTreeRequest;
import com.blog.biz.model.response.CategoryTreeResponse;
import com.blog.biz.service.manager.CategoryManagerService;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "分类管理")
@RestController
@RequestMapping("/portal/categories")
public class
CategoryPortalController {

    private final CategoryManagerService categoryManagerService;

    @Operation(summary = "查询文章分类树")
    @GetMapping
    public Result<List<CategoryTreeResponse>> tree(@ParameterObject CategoryAdminTreeRequest request) {
        return Result.success(categoryManagerService.tree(request));
    }
}
