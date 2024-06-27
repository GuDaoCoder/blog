package com.blog.common.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author zouzhangpeng
 * @desc
 */
public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
    }

    public DataNotFoundException(String errorMsg, Object... args) {
        super(MessageFormatter.arrayFormat(errorMsg, args).getMessage());
    }

}
