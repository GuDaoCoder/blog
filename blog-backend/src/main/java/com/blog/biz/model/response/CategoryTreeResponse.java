package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import com.blog.common.tree.TreeNode;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryTreeResponse extends CategoryResponse implements TreeNode<CategoryTreeResponse> {

    @Serial
    private static final long serialVersionUID = -5751674634148658873L;

    @Schema(description = "子级数据")
    private List<CategoryTreeResponse> children;

    @Override
    public void buildChildren(List<CategoryTreeResponse> children) {
        this.children = children;
    }
}
