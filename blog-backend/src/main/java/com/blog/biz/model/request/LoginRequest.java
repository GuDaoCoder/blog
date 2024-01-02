package com.blog.biz.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class LoginRequest {

    @Schema(name = "用户名")
    private String username;

    @Schema(name = "密码")
    private String password;
}
