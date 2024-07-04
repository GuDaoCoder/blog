package com.blog.biz.controller.admin;

import com.blog.biz.model.request.TaskSearchRequest;
import com.blog.biz.model.response.TaskResponse;
import com.blog.biz.service.manager.TaskManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 任务信息表 前端控制器
 * </p>
 *
 * @author zane.zou
 * @since 2024-06-28
 */
@Tag(name = "任务信息管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/tasks")
public class TaskAdminController {

    private final TaskManagerService taskManagerService;

    @Operation(summary = "查询任务列表")
    @GetMapping
    public Result<PageResponse<TaskResponse>> search(@ParameterObject TaskSearchRequest request) {
        return Result.success(taskManagerService.search(request));
    }

}
