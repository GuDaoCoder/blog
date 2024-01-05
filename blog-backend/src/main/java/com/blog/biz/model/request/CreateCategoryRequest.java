package com.blog.biz.model.request;

import com.blog.common.base.request.CommonRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serial;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateCategoryRequest extends CommonRequest {

    @Serial
    private static final long serialVersionUID = 6089701855940646688L;

    @NotBlank(message = "分类名称不能为空")
    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "上级分类")
    private Long parentId;

}
