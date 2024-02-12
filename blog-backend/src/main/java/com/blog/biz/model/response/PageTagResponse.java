package com.blog.biz.model.response;

import java.io.Serial;

import com.blog.common.base.response.CommonResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zouzhangpeng
 * @desc
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class PageTagResponse extends CommonResponse {

    @Serial
    private static final long serialVersionUID = 5843521204175924852L;


    @Schema(description = "标签Id")
    private Long tagId;

    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "颜色")
    private String color;

    @Schema(description = "文章数")
    private Long postCount;

    @Schema(description = "是否启用")
    private Boolean enable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private String updateTime;
}
