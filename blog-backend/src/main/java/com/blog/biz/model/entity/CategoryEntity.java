package com.blog.biz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_category")
public class CategoryEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -4741675516178730101L;

    @TableId(type = IdType.AUTO)
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 上级分类
     */
    private Long parentCategoryId;

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

}
