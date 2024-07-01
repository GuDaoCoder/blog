package com.blog.biz.service.manager;

public interface TaskManagerService {

    /**
     * 记录任务开始运行
     *
     * @param taskName
     * @param description
     * @return Long
     **/
    Long recordRunTask(String taskName, String description);

    /**
     * 记录任务失败
     * @param taskId
     * @param ex
     * @param description
     * @return void
     **/
    void recordFailTask(Long taskId, Throwable ex, String description);

    /**
     * 记录任务结束
     *
     * @param taskId
     * @return void
     **/
    void recordEndTask(Long taskId, String description);
}
