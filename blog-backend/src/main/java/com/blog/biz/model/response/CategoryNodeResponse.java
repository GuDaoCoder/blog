package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import com.blog.common.tree.TreeNode;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class CategoryNodeResponse extends CommonResponse implements TreeNode<CategoryNodeResponse> {

    @Serial
    private static final long serialVersionUID = -5751674634148658873L;

    @Schema(description = "分类Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "上级分类")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;

    @Schema(description = "全路径Id")
    private String fullId;

    @Schema(description = "顺序")
    private Integer orderNo;

    @Schema(description = "级别")
    private Integer level;

    @Schema(description = "子级数据")
    private List<CategoryNodeResponse> children;

    @Override
    public void buildChildren(List<CategoryNodeResponse> children) {
        this.children = children;
    }
}
