package com.blog.common.base.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zouzhangpeng
 * @desc 请求入参实体基类
 */
@Data
public class CommonRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 4910802190284215606L;

}
