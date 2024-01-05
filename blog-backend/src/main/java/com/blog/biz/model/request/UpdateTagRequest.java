package com.blog.biz.model.request;

import java.io.Serial;

import javax.validation.constraints.NotBlank;

import com.blog.common.base.request.CommonRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateTagRequest extends CommonRequest {

    @Serial
    private static final long serialVersionUID = 6475559052331430554L;

    @NotBlank(message = "标签名称不能为空")
    @Schema(description = "标签名称")
    private String tagName;
}