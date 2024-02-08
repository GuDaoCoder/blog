package com.blog.biz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
@Accessors(chain = true)
@TableName("t_category")
public class CategoryEntity extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
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

    /**
     * 是否启用
     */
    private Boolean enabled;

}
