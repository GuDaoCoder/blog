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
@TableName("t_post_tag_rela")
public class PostTagRelaEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = -7442565056566303901L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Long postTagRelaId;

	/**
	 * 文章Id
	 */
	private Long postId;

	/**
	 * 标签Id
	 */
	private Long tagId;

}
