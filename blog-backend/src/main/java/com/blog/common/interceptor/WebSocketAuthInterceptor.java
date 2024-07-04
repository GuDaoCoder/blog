package com.blog.common.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.blog.common.annotation.Ws;
import com.blog.common.base.service.UserDetailLoadService;
import com.blog.common.constant.Constants;
import com.blog.common.util.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private final UserDetailLoadService userDetailLoadService;

    private Set<String> loginRequiredWsUrls;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        // 判断当前请求是否需要认证
        boolean loginRequired = getLoginRequiredWsUrls().contains(request.getURI().getPath());
        if (loginRequired && !StpUtil.isLogin()) {
            return false;
        }
        if (loginRequired) {
            attributes.put(Constants.WS_USER_DETAIL, userDetailLoadService.load((String) StpUtil.getLoginId()));
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
    }

    private Set<String> getLoginRequiredWsUrls() {
        if (loginRequiredWsUrls == null) {
            loginRequiredWsUrls = new HashSet<>();
            Map<String, Object> wsBeans = SpringUtil.getBeansWithAnnotation(Ws.class);
            wsBeans.forEach((beanName, bean) -> {
                Ws ws = AnnotationUtils.findAnnotation(bean.getClass(), Ws.class);
                if (ws != null && ws.loginRequired()) {
                    loginRequiredWsUrls.add(ws.url());
                }
            });
        }
        return loginRequiredWsUrls;
    }

}
