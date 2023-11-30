package com.blog.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.blog.common.context.RequestContext;
import com.blog.common.util.ServletUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求拦截器
 *
 * @author zouzhangpeng
 */
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestContext.Info requestInfo = new RequestContext.Info();

        // 请求ID
        requestInfo.setRequestId(ServletUtils.getRequestId(request));

        RequestContext.set(requestInfo);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestContext.remove();
    }
}
