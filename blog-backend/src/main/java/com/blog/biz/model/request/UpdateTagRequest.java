package com.blog.biz.model.request;

import java.io.Serial;

import javax.validation.constraints.NotBlank;

import com.blog.common.base.request.CommonRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class UpdateTagRequest extends CommonRequest {

    @Serial
    private static final long serialVersionUID = 6475559052331430554L;

    @NotBlank
    @Schema(description = "标签名称")
    private String tagName;
}
