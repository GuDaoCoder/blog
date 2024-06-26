package com.blog.common.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.blog.common.base.service.UserDetailLoadService;
import com.blog.common.context.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zouzhangpeng
 * @desc 用户拦截器
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UserInterceptor implements HandlerInterceptor {

    private final UserDetailLoadService userDetailLoadService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UserContext.set(userDetailLoadService.load((String) StpUtil.getLoginId()));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        UserContext.remove();
    }

}
