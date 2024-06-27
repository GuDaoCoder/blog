package com.blog.common.util;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author zouzhangpeng
 * @desc
 */
public class StrUtil {

    public static String format(String template, Object... args) {
        return MessageFormatter.arrayFormat(template, args).getMessage();
    }

}
