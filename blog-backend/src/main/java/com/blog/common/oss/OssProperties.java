package com.blog.common.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    /**
     * ak
     */
    private String accessKey;

    /**
     * sk
     */
    private String secretKey;

    /**
     * bucket
     */
    private String bucketName;

    /**
     * 域名
     */
    private String domain;

    /**
     * 图床文件夹
     */
    private String directory;

}
