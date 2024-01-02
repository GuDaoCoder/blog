package com.blog.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
@Component
@ConfigurationProperties(prefix = "sa-token", ignoreInvalidFields = true)
public class SaTokenProperty {

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
