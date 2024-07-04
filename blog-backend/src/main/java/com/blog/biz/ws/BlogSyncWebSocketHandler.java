package com.blog.biz.ws;

import com.blog.biz.service.manager.BlogSyncService;
import com.blog.common.annotation.Ws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
@Ws(url = "/ws/admin/blog/sync", loginRequired = true)
public class BlogSyncWebSocketHandler extends TextWebSocketHandler {

    private final BlogSyncService blogSyncService;

    /**
     * 监听连接开启
     * @param session
     * @return void
     **/
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        blogSyncService.sync(session);
    }

    /**
     * 监听连接关闭
     * @param session
     * @param status
     * @return void
     **/
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    }

    /**
     * 收到消息
     * @param session
     * @param message
     * @return void
     **/
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
    }

    /**
     * 向指定客户端推送消息
     * @param session
     * @param message
     */
    public static void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
