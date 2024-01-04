package com.blog.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
@ConfigurationProperties(prefix = "sa-token", ignoreInvalidFields = true)
public class SaTokenProperties {

    /**
     * token 名称
     */
    private String tokenName;

    /**
     * token 有效期
     */
    private Integer timeout;

    /**
     * token 最低活跃频率
     */
    private Integer activeTimeout;
}
