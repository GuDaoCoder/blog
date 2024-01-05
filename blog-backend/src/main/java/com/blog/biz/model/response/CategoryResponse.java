package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class CategoryResponse extends CommonResponse {

    @Serial
    private static final long serialVersionUID = -5751674634148658873L;

    @Schema(description = "分类Id")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "上级分类")
    private Long parentId;

    @Schema(description = "全路径Id")
    private String fullId;

    @Schema(description = "顺序")
    private Integer orderNo;

    @Schema(description = "级别")
    private Integer level;
}
