package com.blog.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.blog.common.context.RequestIdContext;
import com.blog.common.util.ServletUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc 请求Id拦截器
 */
@Slf4j
@Component
public class RequestIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestIdContext.set(ServletUtil.getRequestId(request));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        RequestIdContext.remove();
    }
}
