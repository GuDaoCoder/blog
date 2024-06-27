package com.blog.biz.model.request;

import com.blog.common.base.request.CommonRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class GitRepositoryRequest extends CommonRequest {

    @Serial
    private static final long serialVersionUID = 6507612400889517432L;

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

}
