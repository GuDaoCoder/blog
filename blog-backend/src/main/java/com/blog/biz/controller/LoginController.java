package com.blog.biz.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.blog.biz.model.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.biz.model.vo.LoginVO;
import com.blog.common.domain.Result;

/**
 * @author zouzhangpeng
 * @desc
 */
@Tag(name = "登录认证")
@RestController
public class LoginController {

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        StpUtil.login(1L);
        return Result.success(new LoginVO(StpUtil.getTokenValue()));
    }
}
