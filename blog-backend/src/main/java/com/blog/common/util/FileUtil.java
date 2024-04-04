package com.blog.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.UUID;

public class FileUtil {

    public static String resetFileName(String originalFileName) {
        if (StringUtils.isBlank(originalFileName)) {
            throw new IllegalArgumentException("文件名称不能为空");
        }
        String ext = "." + originalFileName.split("\\.")[1];
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid + ext;
    }

    /**
     * 使用uuid重命名文件名称
     *
     * @param fileName
     * @return String
     **/
    public static String uuidFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException("文件名称不能为空");
        }
        int extIndex = fileName.lastIndexOf(".");
        String ext = fileName.substring(extIndex);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid + ext;
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return boolean
     **/
    public static boolean exists(String path) {
        return path != null && new File(path).exists();
    }
}
