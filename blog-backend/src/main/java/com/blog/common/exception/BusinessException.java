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

    private String code;

    public BusinessException(String errorMsg, Objects... args) {
        super(MessageFormatter.arrayFormat(errorMsg, args).getMessage());
    }

}
