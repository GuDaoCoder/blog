package com.blog.common.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zouzhangpeng
 */
@Data
public class CommonDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4910802190284215606L;

    private String requestId;
}
