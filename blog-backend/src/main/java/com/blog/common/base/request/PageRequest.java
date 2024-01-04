package com.blog.common.base.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Pageable;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class PageRequest extends CommonRequest {

    @Schema(description = "页码")
    private Integer pageNumber;

    @Schema(description = "每页数量")
    private Integer pageSize;

    public Pageable pageable() {
        if (this.pageNumber == null) {
            this.pageNumber = 1;
        }
        if (this.pageSize == null) {
            this.pageSize = 10;
        }
        return org.springframework.data.domain.PageRequest.of(pageNumber - 1, pageSize);
    }
}
