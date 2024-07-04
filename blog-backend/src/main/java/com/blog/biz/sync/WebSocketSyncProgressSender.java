package com.blog.biz.sync;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketSyncProgressSender implements SyncProgressSender {

    private final WebSocketSession session;

    public WebSocketSyncProgressSender(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public void send(String message) throws IOException {
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }

}
