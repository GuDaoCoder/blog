package com.blog.common.exception;

import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;

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
