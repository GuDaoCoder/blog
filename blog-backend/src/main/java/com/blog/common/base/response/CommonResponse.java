package com.blog.common.base.response;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc 返回出参实体基类
 */
@Data
public abstract class CommonResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5031608270970621589L;

}
