package com.blog.biz.model.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.blog.common.base.request.CommonRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class UpdatePostRequest extends CommonRequest {

    @NotBlank(message = "文章标题不能为空")
    @Schema(description = "标题")
    private String title;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "封面图片链接")
    private String coverPictureUrl;

    @NotNull(message = "所属分类Id不能为空")
    @Schema(description = "所属分类Id")
    private Long categoryId;

    @NotNull(message = "是否置顶不能为空")
    @Schema(description = "是否置顶")
    private Boolean top;

    @NotNull(message = "是否开启评论不能为空")
    @Schema(description = "是否开启评论")
    private Boolean enableComment;

    @Schema(description = "访问密码")
    private String password;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "标签集合")
    private List<Long> tagIds;
}
