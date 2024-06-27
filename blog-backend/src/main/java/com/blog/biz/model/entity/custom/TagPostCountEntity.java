package com.blog.biz.model.entity.custom;

import lombok.Data;

@Data
public class TagPostCountEntity {

	/**
	 * 标签Id
	 */
	private Long tagId;

	/**
	 * 文章数量
	 */
	private Long postCount;

}
