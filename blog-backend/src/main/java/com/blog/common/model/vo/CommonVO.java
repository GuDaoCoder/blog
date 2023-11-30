package com.blog.common.model.vo;

import com.blog.common.context.RequestContext;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zouzhangpeng
 */
@Data
public class CommonVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5031608270970621589L;

    private String requestId = RequestContext.getRequestId();
}
