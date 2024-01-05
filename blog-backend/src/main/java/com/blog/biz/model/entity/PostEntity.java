package com.blog.biz.model.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "t_post")
public class PostEntity extends BaseEntity {

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "snowflakeId", strategy = "com.blog.common.jpa.config.IdGeneratorConfig")
    @GeneratedValue(generator = "snowflakeId")
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
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    /**
     * 文章来源
     */
    @Enumerated(EnumType.STRING)
    private PostSource source;

    /**
     * 所属分类Id
     */
    private Long categoryId;

    /**
     * 是否发布
     */
    @Column(name = "is_publish")
    private Boolean publish;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 是否置顶
     */
    @Column(name = "is_top")
    private Boolean top;

    /**
     * 是否开启评论
     */
    @Column(name = "is_enable_comment")
    private Boolean enableComment;

    /**
     * 是否设置加密访问
     */
    @Column(name = "is_encrypt")
    private Boolean encrypt;

    /**
     * 访问密码
     */
    private String password;

    @Override
    public Long primaryId() {
        return this.postId;
    }
}
