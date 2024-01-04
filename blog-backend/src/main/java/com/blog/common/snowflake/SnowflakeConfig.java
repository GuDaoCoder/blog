package com.blog.common.snowflake;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zouzhangpeng
 * @desc 雪花算法
 */
@RequiredArgsConstructor
@Configuration
public class SnowflakeConfig {

    private final SnowflakeProperties snowflakeProperties;

    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(snowflakeProperties.getWorkerId(), snowflakeProperties.getDatacenterId());
    }
}
