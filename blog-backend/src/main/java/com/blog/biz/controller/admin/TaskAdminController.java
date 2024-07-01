package com.blog.biz.controller.admin;

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
@Tag(name = "任务信息表")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/taskEntity")
public class TaskAdminController {

}
