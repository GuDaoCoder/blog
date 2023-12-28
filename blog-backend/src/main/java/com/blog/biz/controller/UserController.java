package com.blog.biz.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.common.domain.Result;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author zouzhangpeng
 * @desc
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {


    @Operation(summary = "新增用户")
    @SaCheckPermission("user:add")
    @PostMapping
    public Result<Void> create() {
        return Result.success();
    }
}
