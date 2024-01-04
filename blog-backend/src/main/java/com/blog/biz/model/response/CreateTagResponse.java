package com.blog.biz.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@AllArgsConstructor
@Data
public class CreateTagResponse {

    @Schema(description = "标签Id")
    private Long tagId;
}
