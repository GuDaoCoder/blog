package com.blog.biz.model.entity;

import com.blog.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
@Accessors(chain = true)
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

}
