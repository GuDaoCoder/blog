package com.blog.common.oss;

import com.blog.common.oss.qiniu.QiniuOssApiServiceImpl;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {

    @Bean
    public OssApiService ossApiService(OssProperties ossProperties, UploadManager uploadManager, Auth auth) {
        return new QiniuOssApiServiceImpl(ossProperties, uploadManager, auth);
    }
}
