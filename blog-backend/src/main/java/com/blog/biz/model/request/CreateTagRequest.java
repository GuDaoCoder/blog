package com.blog.biz.model.request;

import com.blog.common.base.request.CommonRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateTagRequest extends CommonRequest {

    @Serial
    private static final long serialVersionUID = 6475559052331430554L;

    @NotBlank(message = "标签名称不能为空")
    @Schema(description = "标签名称")
    private String tagName;

    @NotBlank(message = "颜色不能为空")
    @Schema(description = "颜色")
    private String color;

    @NotNull(message = "是否启用不能为空")
    @Schema(description = "是否启用")
    private Boolean enable;

}
