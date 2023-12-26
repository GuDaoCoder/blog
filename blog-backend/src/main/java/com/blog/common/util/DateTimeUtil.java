package com.blog.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author zouzhangpeng
 * @desc 日期时间工具类
 */
public class DateTimeUtil {

    /**
     * 获取当前时间戳
     * 
     * @return java.lang.Long
     */
    public static Long timestamp() {
        return LocalDateTime.now().atOffset(ZoneOffset.UTC).toEpochSecond();
    }
}
