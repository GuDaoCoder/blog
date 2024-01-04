package com.blog.common.properties;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc 安全相关配置信息
 */
@Data
@ConfigurationProperties(prefix = "security", ignoreInvalidFields = true)
public class SecurityProperties {

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
