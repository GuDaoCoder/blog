package com.blog.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zouzhangpeng
 * @desc 性别
 */
@Getter
@AllArgsConstructor
public enum Gender {

    MALE("男性"),

    FEMALE("女性"),

    UNKNOWN("未知"),

    ;

    private String label;

    @Override
    public String toString(){
        return this.name()+"-"+this.label;
    }
}
