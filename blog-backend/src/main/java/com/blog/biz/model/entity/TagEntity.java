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
@TableName("t_tag")
public class TagEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 5214190302074473126L;

    /**
     * 用户Id
     */
    @TableId(type = IdType.AUTO)
    private Long tagId;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 颜色
     */
    private String color;

}
