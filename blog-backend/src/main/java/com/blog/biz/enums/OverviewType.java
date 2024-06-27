package com.blog.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OverviewType {

    CATEGORY("分类"),

    TAG("标签"),

    POST("文章");

    private final String label;

    @Override
    public String toString() {
        return this.name() + "-" + this.label;
    }

}
