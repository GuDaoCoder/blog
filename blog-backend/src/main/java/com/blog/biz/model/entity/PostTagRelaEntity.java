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
@Table(name = "t_post_tag_rela")
public class PostTagRelaEntity extends BaseEntity {

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "snowflakeId", strategy = "com.blog.common.jpa.config.IdGeneratorConfig")
    @GeneratedValue(generator = "snowflakeId")
    private Long postTagRelaId;

    /**
     * 文章Id
     */
    private Long postId;

    /**
     * 标签Id
     */
    private Long tagId;

    @Override
    public Long primaryId() {
        return this.postTagRelaId;
    }
}
