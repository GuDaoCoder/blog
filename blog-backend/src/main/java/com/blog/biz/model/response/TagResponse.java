package com.blog.biz.model.response;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class TagResponse {

    @Serial
    private static final long serialVersionUID = 5843521204175924852L;

    @Schema(description = "用户Id")
    private Long tagId;

    @Schema(description = "标签名称")
    private String tagName;

    @Schema(description = "标签顺序")
    private Integer orderNo;
}
