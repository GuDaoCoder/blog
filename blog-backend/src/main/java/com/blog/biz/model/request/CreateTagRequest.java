package com.blog.biz.model.request;

import com.blog.common.base.request.CommonRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serial;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class CreateTagRequest extends CommonRequest {

    @Serial
    private static final long serialVersionUID = 6475559052331430554L;

    @NotBlank
    @Schema(description = "标签名称")
    private String tagName;
}
