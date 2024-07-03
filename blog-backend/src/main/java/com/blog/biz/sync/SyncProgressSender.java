package com.blog.biz.sync;

import com.blog.common.support.GitOperation;

public interface SyncProgressSender extends GitOperation.GitOperationLogSender {

    /**
     * 发送监控进度信息
     * @param message
     * @return void
     **/
    void send(String message) throws Exception;

}
