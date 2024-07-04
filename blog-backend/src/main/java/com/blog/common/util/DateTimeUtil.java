package com.blog.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author zouzhangpeng
 * @desc 日期时间工具类
 */
public class DateTimeUtil {

    /**
     * 标准日期格式
     */
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间戳
     * @return java.lang.Long
     */
    public static Long timestamp() {
        return LocalDateTime.now().atOffset(ZoneOffset.UTC).toEpochSecond();
    }

    public static String nowStr(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String nowStr() {
        return nowStr(STANDARD_FORMAT);
    }

}
