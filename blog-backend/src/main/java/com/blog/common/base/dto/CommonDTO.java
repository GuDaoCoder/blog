package com.blog.common.base.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zouzhangpeng
 * @desc  请求入参实体基类
 */
@Data
public class CommonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4910802190284215606L;

    private String requestId;
}
