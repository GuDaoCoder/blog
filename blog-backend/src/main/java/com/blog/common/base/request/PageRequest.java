package com.blog.common.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class PageRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 7680283922269033786L;

	@Schema(description = "页码")
	private Long pageNumber;

	@Schema(description = "每页数量")
	private Long pageSize;

}
