package com.blog.biz.model.response;

import java.time.LocalDateTime;
import java.util.List;

import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.common.base.response.CommonResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
public class PagePostResponse extends CommonResponse {

    @Schema(description = "主键")
    private Long postId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "封面图片链接")
    private String coverPictureUrl;

    @Schema(description = "状态")
    private PostStatus status;

    @Schema(description = "文章来源")
    private PostSource source;

    @Schema(description = "所属分类Id")
    private Long categoryId;

    @Schema(description = "所属分类名称")
    private String categoryName;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否置顶")
    private Boolean top;

    @Schema(description = "是否开启评论")
    private Boolean enableComment;

    @Schema(description = "访问密码")
    private String password;

    @Schema(description = "标签集合")
    private List<TagResponse> tags;
}
