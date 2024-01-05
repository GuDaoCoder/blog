package com.blog.biz.model.entity;

import javax.persistence.*;

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
@Access(AccessType.FIELD)
@Entity
@Table(name = "t_category")
public class CategoryEntity extends BaseEntity {

    @Id
    @GenericGenerator(name = "snowflakeId", strategy = "com.blog.common.jpa.config.IdGeneratorConfig")
    @GeneratedValue(generator = "snowflakeId")
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 上级分类
     */
    private Long parentId;

    /**
     * 全路径Id
     */
    private String fullId;

    /**
     * 顺序
     */
    private Integer orderNo;

    /**
     * 级别
     */
    private Integer level;

    @Override
    public Long primaryId() {
        return this.categoryId;
    }
}
