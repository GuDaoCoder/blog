package com.blog.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zouzhangpeng
 * @desc 文章来源
 */
@Getter
@AllArgsConstructor
public enum PostSource {

    ADD("正常新增"),

    IMPORT_MD("markdown文档导入"),

    ;

    private String label;
}
