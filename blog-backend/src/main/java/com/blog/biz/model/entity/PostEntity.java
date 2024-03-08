package com.blog.biz.model.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_post")
public class PostEntity extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long postId;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String summary;

    /**
     * 封面图片链接
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String coverPictureUrl;

    /**
     * 状态
     */
    private PostStatus status;

    /**
     * 文章来源
     */
    private PostSource source;

    /**
     * 所属分类Id
     */
    private Long categoryId;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 是否开启评论
     */
    private Boolean enableComment;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 下架时间
     */
    private LocalDateTime removeTime;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

}
