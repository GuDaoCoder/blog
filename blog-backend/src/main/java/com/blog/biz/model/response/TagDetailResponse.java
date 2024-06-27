package com.blog.biz.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TagDetailResponse extends TagResponse {

    @Serial
    private static final long serialVersionUID = 9023790080780055793L;

    @Schema(description = "文章数")
    private Long postCount;

}
