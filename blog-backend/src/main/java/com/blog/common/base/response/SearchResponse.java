package com.blog.common.base.response;

import java.io.Serial;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
public class SearchResponse<Response> extends CommonResponse {

    @Serial
    private static final long serialVersionUID = 7162988863813899849L;

    @Schema(description = "页码")
    private Long pageNumber;

    @Schema(description = "每页数量")
    private Long pageSize;

    @Schema(description = "总数")
    private Long total;

    @Schema(description = "分页数据")
    private List<Response> items;
}
