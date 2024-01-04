package com.blog.biz.model.entity;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签顺序
     */
    private Integer orderNo;

}
