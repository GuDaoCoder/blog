package com.blog.common.exception;

import java.util.Objects;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author zouzhangpeng
 * @desc
 */
public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(){
    }

    public DataNotFoundException(String errorMsg, Objects... args){
        super(MessageFormatter.arrayFormat(errorMsg,args).getMessage());
    }
}
