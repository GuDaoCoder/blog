package com.blog.biz.sync;

import lombok.Getter;

@Getter
public class TaskSyncProgressSender implements SyncProgressSender {

    private final StringBuffer log;

    public TaskSyncProgressSender() {
        this.log = new StringBuffer();
    }

    @Override
    public void send(String message) {
        log.append(message);
    }

}
