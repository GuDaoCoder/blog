package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class GitRepositoryResponse extends CommonResponse {

	@Serial
	private static final long serialVersionUID = 1677824411308938603L;

	@Schema(description = "主键")
	private Long gitRepositoryId;

	@Schema(description = "仓库地址")
	private String url;

	@Schema(description = "本地下载路径")
	private String localPath;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "密码")
	private String password;

	@Schema(description = "分支")
	private String branch;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}
