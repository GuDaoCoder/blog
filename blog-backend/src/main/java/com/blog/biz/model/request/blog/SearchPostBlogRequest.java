package com.blog.biz.model.request.blog;

import com.blog.common.base.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchPostBlogRequest extends PageRequest {

    @Schema(description = "标题")
    private String title;

}
