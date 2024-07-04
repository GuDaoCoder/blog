package com.blog.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.blog.common.interceptor.RequestIdInterceptor;
import com.blog.common.interceptor.UserInterceptor;
import com.blog.common.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author zouzhangpeng
 */
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;

    private final RequestIdInterceptor requestIdInterceptor;

    private final UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求拦截器
        registry.addInterceptor(requestIdInterceptor).addPathPatterns("/**");
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/api/**")
                .excludePathPatterns(securityProperties.getWhiteUrls().toArray(new String[0]));
        // 用户拦截器
        registry.addInterceptor(userInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns(new ArrayList<>(securityProperties.getWhiteUrls()));
    }

    /**
     * 跨域配置
     * @param
     * @return org.springframework.web.filter.CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置允许访问源地址
        config.addAllowedOriginPattern("*");
        // 设置允许访问源请求头
        config.addAllowedHeader("*");
        // 设置允许访问源请求方法
        config.addAllowedMethod("*");
        // 设置有效期1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }

}
