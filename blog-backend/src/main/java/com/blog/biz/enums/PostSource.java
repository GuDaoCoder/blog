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

    MD_IMPORT("markdown导入"),

    MD_SYNC("markdown同步");;

    private String label;

    @Override
    public String toString() {
        return this.name() + "-" + this.label;
    }
}
