package com.blog.common.property;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc 项目配置信息
 */
@Data
@Component
@ConfigurationProperties(prefix = "config",ignoreInvalidFields = true)
public class ConfigProperty {

    /**
     * token名称
     */
    private String tokenName;

    /**
     * jwt加密盐
     */
    private String jwtSalt;

    /**
     * 白名单
     */
    private Set<String> whiteUrls;

}
