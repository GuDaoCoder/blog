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
public class UpdateCategoryRequest extends CommonRequest {

    @Serial
    private static final long serialVersionUID = -2801296631270703763L;

    @NotBlank(message = "分类名称不能为空")
    @Schema(description = "分类名称")
    private String categoryName;

    @NotNull(message = "是否启用不能为空")
    @Schema(description = "是否启用")
    private Boolean enabled;

}
