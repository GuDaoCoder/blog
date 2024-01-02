package com.blog.common.property;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc 安全相关配置信息
 */
@Data
@Component
@ConfigurationProperties(prefix = "security", ignoreInvalidFields = true)
public class SecurityProperty {

    /**
     * rsa配置信息
     */
    private Rsa rsa;

    /**
     * 白名单
     */
    private Set<String> whiteUrls;

    @Data
    public static class Rsa {

        /**
         * 私钥
         */
        private String privateKey;

        /**
         * 公钥
         */
        private String publicKey;
    }

}
