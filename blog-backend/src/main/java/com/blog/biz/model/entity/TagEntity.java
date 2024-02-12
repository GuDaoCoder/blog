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
@TableName("t_tag")
public class TagEntity extends BaseEntity {

    /**
     * 用户Id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long tagId;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 颜色
     */
    private String color;

    /**
     * 是否启用
     */
    private Boolean enable;

}
