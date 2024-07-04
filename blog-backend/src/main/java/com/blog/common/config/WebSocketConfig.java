package com.blog.common.config;

import com.blog.common.annotation.Ws;
import com.blog.common.interceptor.WebSocketAuthInterceptor;
import com.blog.common.util.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketAuthInterceptor webSocketAuthInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        Map<String, Object> wsBeans = SpringUtil.getBeansWithAnnotation(Ws.class);
        if (MapUtils.isNotEmpty(wsBeans)) {
            for (Map.Entry<String, Object> entry : wsBeans.entrySet()) {
                Object bean = entry.getValue();
                Ws ws = AnnotationUtils.findAnnotation(bean.getClass(), Ws.class);
                if (ws != null && bean instanceof TextWebSocketHandler textWebSocketHandler) {
                    registry.addHandler(textWebSocketHandler, ws.url())
                        .addInterceptors(webSocketAuthInterceptor)
                        .setAllowedOrigins("*");
                    log.info("registry ws url:{},login required:{}", ws.url(), ws.loginRequired());
                }
            }
        }

    }

}
