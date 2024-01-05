package com.blog.biz.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.blog.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "t_tag")
public class TagEntity extends BaseEntity {

    /**
     * 用户Id
     */
    @Id
    @GenericGenerator(name = "snowflakeId", strategy = "com.blog.common.jpa.config.IdGeneratorConfig")
    @GeneratedValue(generator = "snowflakeId")
    private Long tagId;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签顺序
     */
    private Integer orderNo;

    @Override
    public Long primaryId() {
        return this.tagId;
    }
}
