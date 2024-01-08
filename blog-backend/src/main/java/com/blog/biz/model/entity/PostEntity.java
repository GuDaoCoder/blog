package com.blog.biz.model.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("t_post")
public class PostEntity extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long postId;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 封面图片链接
     */
    private String coverPictureUrl;

    /**
     * 字数
     */
    private Integer wordCount;

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
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 是否置顶
     */
    @TableField("is_top")
    private Boolean top;

    /**
     * 是否开启评论
     */
    @TableField("is_enable_comment")
    private Boolean enableComment;

    /**
     * 访问密码
     */
    private String password;

}
