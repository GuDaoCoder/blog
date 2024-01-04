package com.blog.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc 项目属性
 */
@Data
@ConfigurationProperties(prefix = "project",ignoreInvalidFields = true)
public class ProjectProperties {

    /**
     * 项目名称
     */
    private String title;

    /**
     * 项目版本
     */
    private String version;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 官网
     */
    private String website;
}
