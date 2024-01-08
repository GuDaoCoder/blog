package com.blog.biz.model.entity;

import com.blog.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "t_post_content")
public class PostContentEntity extends BaseEntity {

    /**
     * 主键Id
     */
    @Id
    @GenericGenerator(name = "snowflakeId", strategy = "com.blog.common.jpa.config.IdGeneratorConfig")
    @GeneratedValue(generator = "snowflakeId")
    private Long postContentId;

    /**
     * 文章内容
     */
    private String content;

    @Override
    public Long primaryId() {
        return this.postContentId;
    }
}
