package com.blog.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zouzhangpeng
 * @desc 文章状态
 */
@AllArgsConstructor
@Getter
public enum PostStatus {

	DRAFT("草稿"),

	PUBLISHED("已发布"),

	REMOVED("已下架"),;

	private String label;

	@Override
	public String toString() {
		return this.name() + "-" + this.label;
	}

}
