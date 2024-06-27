package com.blog.common.base.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zouzhangpeng
 * @desc 返回出参实体基类
 */
@Data
public abstract class CommonResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 5031608270970621589L;

}
