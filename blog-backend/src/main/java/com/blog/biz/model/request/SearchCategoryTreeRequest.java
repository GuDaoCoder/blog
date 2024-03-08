package com.blog.biz.model.request;

import com.blog.common.base.request.CommonRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchCategoryTreeRequest extends CommonRequest {

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "是否启用")
    private Boolean enabled;
}
