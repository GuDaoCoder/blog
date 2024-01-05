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
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class CreateTagResponse extends CommonResponse {

    @Serial
    private static final long serialVersionUID = -1913806770602840312L;

    @Schema(description = "标签Id")
    private Long tagId;
}
