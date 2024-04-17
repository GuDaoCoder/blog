package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author zouzhangpeng
 * @desc
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryResponse extends CommonResponse {

    @Serial
    private static final long serialVersionUID = 9116953955110935038L;

    @Schema(description = "分类Id")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "上级分类Id")
    private Long parentCategoryId;

    @Schema(description = "上级分类名称")
    private String parentCategoryName;

    @Schema(description = "全路径Id")
    private String fullId;

    @Schema(description = "顺序")
    private Integer orderNo;

    @Schema(description = "级别")
    private Integer level;

    @Schema(description = "是否启用")
    private Boolean enabled;

    @Schema(description = "文章数量")
    private Long postCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
