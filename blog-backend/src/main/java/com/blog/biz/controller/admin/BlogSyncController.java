package com.blog.biz.controller.admin;

import com.blog.biz.service.manager.BlogSyncService;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "文章同步管理")
@RestController
@RequestMapping("/admin/blog")
public class BlogSyncController {

    private final BlogSyncService blogSyncService;

    @Operation(summary = "同步文章")
    @GetMapping("/sync")
    public Result<Void> sync() {
        blogSyncService.sync();
        return Result.success();
    }
}
