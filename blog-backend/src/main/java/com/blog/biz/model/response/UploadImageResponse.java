package com.blog.biz.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UploadImageResponse {

	@Schema(description = "访问url")
	private String url;

}
