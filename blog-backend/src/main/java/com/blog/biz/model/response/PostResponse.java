package com.blog.biz.model.response;

import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.common.base.response.CommonResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostResponse extends CommonResponse {

    @Schema(description = "主键")
    private Long postId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "文章内容")
    private String content;

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

    @Schema(description = "是否置顶")
    private Boolean top;

    @Schema(description = "是否加密")
    private Boolean encrypt;

    @Schema(description = "是否开启评论")
    private Boolean enableComment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "最后更新时间")
    private LocalDateTime updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "下架时间")
    private LocalDateTime removeTime;

    @Schema(description = "标签集合")
    private List<PostResponse.TagItem> tags;

    @AllArgsConstructor
    @Data
    public static class TagItem {

        @Schema(description = "标签Id")
        private Long tagId;

        @Schema(description = "标签名称")
        private String tagName;

        @Schema(description = "颜色")
        private String color;
    }
}
