package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author zouzhangpeng
 * @desc
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateCategoryResponse extends CommonResponse {

    @Serial
    private static final long serialVersionUID = 9116953955110935038L;

    @Schema(description = "分类Id")
    private Long categoryId;
}
