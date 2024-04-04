package com.blog.biz.model.request;

import com.blog.common.base.request.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateCoverPictureRequest extends CommonRequest {

    /**
     * 封面图片
     */
    private String coverPictureUrl;
}
