package com.blog.biz.model.request;

import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.common.base.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostSearchRequest extends PageRequest {

    @Serial
    private static final long serialVersionUID = -7135763634790416208L;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "状态")
    private PostStatus status;

    @Schema(description = "文章来源")
    private PostSource source;

    @Schema(description = "所属分类Id")
    private Long categoryId;

    @Schema(description = "发布开始时间")
    private LocalDateTime publishStartTime;

    @Schema(description = "发布结束时间")
    private LocalDateTime publishEndTime;

    @Schema(description = "创建开始时间")
    private LocalDateTime createStartTime;

    @Schema(description = "创建结束时间")
    private LocalDateTime createEndTime;

    @Schema(description = "是否置顶")
    private Boolean top;

    @Schema(description = "是否开启评论")
    private Boolean enableComment;

}
