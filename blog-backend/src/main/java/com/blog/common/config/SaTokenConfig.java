package com.blog.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blog.common.property.ConfigProperty;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;

/**
 * @author zouzhangpeng
 * @desc Sa-Token配置
 */
@RequiredArgsConstructor
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    private final ConfigProperty configProperty;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin())).addPathPatterns("/**")
            .excludePathPatterns(configProperty.getWhiteUrls().toArray(new String[0]));
    }
}
