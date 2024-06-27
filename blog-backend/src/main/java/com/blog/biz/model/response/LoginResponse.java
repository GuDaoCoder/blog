package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class LoginResponse extends CommonResponse {

    @Serial
    private static final long serialVersionUID = 2576053190868440582L;

    @Schema(description = "token")
    private String token;

}
