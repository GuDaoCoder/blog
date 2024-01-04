package com.blog.biz.model.request;

import com.blog.common.base.request.CommonRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class LoginRequest extends CommonRequest {

    @Serial
    private static final long serialVersionUID = -8615257892969950742L;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;
}
