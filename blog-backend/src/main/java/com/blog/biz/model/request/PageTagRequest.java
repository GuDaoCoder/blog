package com.blog.biz.model.request;

import com.blog.common.base.request.PageRequest;
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
public class PageTagRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = -5932485187665352003L;

    @Schema(description = "标签名称")
    private String tagName;
}
