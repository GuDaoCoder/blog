package com.blog.biz.exception;

import com.blog.common.exception.BusinessException;

import java.io.Serial;

public class BlogSyncException extends BusinessException {

    @Serial
    private static final long serialVersionUID = -3587900564437608437L;

    public BlogSyncException(String errorMsg, Object... arguments) {
        super(errorMsg, arguments);
    }
}
