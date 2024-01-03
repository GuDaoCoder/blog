package com.blog.biz.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.biz.model.request.LoginRequest;
import com.blog.biz.model.response.LoginResponse;
import com.blog.biz.service.manager.AuthManagerService;
import com.blog.common.domain.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * @author zouzhangpeng
 * @desc
 */
@Tag(name = "登录认证")
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final AuthManagerService authManagerService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        return Result.success(authManagerService.login(request));
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        authManagerService.logout();
        return Result.success();
    }

}
