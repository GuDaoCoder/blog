package com.blog.common.snowflake;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zouzhangpeng
 * @desc
 */
@Data
@ConfigurationProperties(prefix = "snowflake",ignoreInvalidFields = true)
public class SnowflakeProperties {

    /**
     * 工作ID
     */
    private Long workerId;

    /**
     * 数据中心ID
     */
    private Long datacenterId;
}
