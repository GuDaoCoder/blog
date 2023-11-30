package com.blog.common.interceptor;

import com.blog.common.util.MdcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.ttl.TransmittableThreadLocal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 *
 * @author zouzhangpeng
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private final TransmittableThreadLocal<StopWatch> stopWatchLocal = new TransmittableThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 记录请求耗时
        StopWatch stopWatch = new StopWatch();
        stopWatchLocal.set(stopWatch);
        stopWatch.start();

        MdcUtils.init();
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        StopWatch stopWatch = stopWatchLocal.get();
        if (stopWatch != null && stopWatch.isRunning()){
            stopWatch.stop();
            log.info("Url[{}]请求耗时：{}",request.getRequestURI(),stopWatch.getTotalTimeMillis());
        }
        stopWatchLocal.remove();

        MdcUtils.clear();
    }
}
