package com.blog.common.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class PageRequest extends CommonRequest {

    @Schema(description = "页码")
    private Long pageNumber;

    @Schema(description = "每页数量")
    private Long pageSize;

}
