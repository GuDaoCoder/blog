package com.blog.biz.model.request;

import com.blog.common.base.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostPortalSearchRequest extends PageRequest {

    @Serial
    private static final long serialVersionUID = -8794622033265901828L;

    @Schema(description = "标题")
    private String title;

}
