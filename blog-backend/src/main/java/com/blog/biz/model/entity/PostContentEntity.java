package com.blog.biz.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.blog.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
@Accessors(chain = true)
@TableName("t_post_content")
public class PostContentEntity extends BaseEntity {

    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Long postContentId;

    /**
     * 文章Id
     */
    private Long postId;

    /**
     * 文章内容
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String content;

}
