package com.blog.common.util;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zouzhangpeng
 * @desc
 */
@Component
public class SnowflakeUtil {

    private static Snowflake snowflake;

    @Autowired
    public SnowflakeUtil(Snowflake snowflake) {
        SnowflakeUtil.snowflake = snowflake;
    }

    /**
     * 雪花算法Id
     * 
     * @param
     * @return java.io.Serializable
     */
    public static Serializable getId() {
        return snowflake.nextId();
    }
}
