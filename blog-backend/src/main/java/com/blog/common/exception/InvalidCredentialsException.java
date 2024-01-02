package com.blog.common.exception;

import com.blog.common.util.StrUtil;
import lombok.NoArgsConstructor;

/**
 * @author zouzhangpeng
 * @desc 认证失败异常
 */
@NoArgsConstructor
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message, Object... args) {
        super(StrUtil.format(message, args));
    }

}
