package com.blog.common.base.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class NodeResponse<T> extends CommonResponse {

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "显示值")
    private String label;

    @Schema(description = "上级Id")
    private Long parentId;

    @Schema(description = "业务数据")
    private T data;

    @Schema(description = "子集数据")
    private List<NodeResponse<T>> children;

}
