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

    DELETED("已删除"),

    ;

    private String label;
}
