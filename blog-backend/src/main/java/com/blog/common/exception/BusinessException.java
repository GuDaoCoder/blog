package com.blog.common.exception;

import java.util.Objects;

import org.slf4j.helpers.MessageFormatter;

import lombok.Getter;

/**
 * @author zouzhangpeng
 * @desc
 */
@Getter
public class BusinessException extends RuntimeException {

    public BusinessException(String errorMsg, Object... arguments) {
        super(MessageFormatter.arrayFormat(errorMsg, arguments).getMessage());
    }

}
