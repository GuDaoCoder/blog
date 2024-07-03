package com.blog.biz.service.manager;

import org.springframework.web.socket.WebSocketSession;

public interface BlogSyncService {

    void sync();

    void sync(WebSocketSession session);

}
