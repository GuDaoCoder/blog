package com.blog.biz.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CategoryAdminTreeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -47502554648611268L;

    @Schema(description = "分类名称")
    private String categoryName;
}
