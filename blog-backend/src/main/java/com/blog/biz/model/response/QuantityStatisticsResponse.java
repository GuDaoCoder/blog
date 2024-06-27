package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class QuantityStatisticsResponse extends CommonResponse {

	@Serial
	private static final long serialVersionUID = -809500293879766952L;

	@Schema(description = "名称")
	private String name;

	@Schema(description = "显示值")
	private String label;

	@Schema(description = "数量")
	private Long quantity;

}
