package com.blog.common.config;

import com.blog.common.interceptor.RequestIdInterceptor;
import com.blog.common.interceptor.UserInterceptor;
import com.blog.common.property.SecurityProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.stream.Collectors;

/**
 * @author zouzhangpeng
 */
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SecurityProperty securityProperty;

    private final RequestIdInterceptor requestIdInterceptor;

    private final UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求拦截器
        registry.addInterceptor(requestIdInterceptor).addPathPatterns("/**");
        // 用户拦截器
        registry.addInterceptor(userInterceptor).addPathPatterns("/**")
            .excludePathPatterns(securityProperty.getWhiteUrls().stream().collect(Collectors.toList()));
    }

    /**
     * 跨域配置
     *
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
