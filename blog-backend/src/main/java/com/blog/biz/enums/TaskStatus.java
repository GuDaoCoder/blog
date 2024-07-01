package com.blog.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskStatus {

    RUNNING("运行中"),

    SUCCESS("成功"),

    FAIL("失败"),;

    private String label;

    @Override
    public String toString() {
        return this.name() + "-" + this.label;
    }

}
