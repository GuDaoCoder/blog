package com.blog.common.exception;

import com.blog.common.base.IBaseErrorCode;
import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;

import java.util.Objects;

/**
 * @author zouzhangpeng
 * @desc
 */
@Getter
public class BusinessException extends RuntimeException{

    private String code;

    public BusinessException(String errorMsg, Objects... args){
        super(MessageFormatter.arrayFormat(errorMsg,args).getMessage());
    }

    public BusinessException(IBaseErrorCode errorCode){
        super(errorCode.message());
        this.code = errorCode.code();
    }
}
