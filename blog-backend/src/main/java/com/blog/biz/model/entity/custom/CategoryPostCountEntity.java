package com.blog.biz.model.entity.custom;

import lombok.Data;

@Data
public class CategoryPostCountEntity {

	/**
	 * 分类Id
	 */
	private Long categoryId;

	/**
	 * 数量
	 */
	private Long postCount;

}
