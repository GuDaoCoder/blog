package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@AllArgsConstructor
@Data
public class CreatePostResponse extends CommonResponse {

    @Schema(description = "文章Id")
    private Long postId;
}
