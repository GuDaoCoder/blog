package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import com.blog.common.tree.TreeNode;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class TreeCategoryResponse extends CommonResponse implements TreeNode<TreeCategoryResponse> {

    @Serial
    private static final long serialVersionUID = -5751674634148658873L;

    @Schema(description = "分类Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "上级分类")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentCategoryId;

    @Schema(description = "全路径Id")
    private String fullId;

    @Schema(description = "顺序")
    private Integer orderNo;

    @Schema(description = "级别")
    private Integer level;

    @Schema(description = "是否启用")
    private Boolean enabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "子级数据")
    private List<TreeCategoryResponse> children;

    @Override
    public void buildChildren(List<TreeCategoryResponse> children) {
        this.children = children;
    }
}
