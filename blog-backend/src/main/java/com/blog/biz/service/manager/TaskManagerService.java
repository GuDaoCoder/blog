package com.blog.biz.service.manager;

import com.blog.biz.model.request.TaskSearchRequest;
import com.blog.biz.model.response.TaskResponse;
import com.blog.common.base.response.PageResponse;

public interface TaskManagerService {

    /**
     * 查询任务信息
     * @param request
     * @return PageResponse<TaskResponse>
     **/
    PageResponse<TaskResponse> search(TaskSearchRequest request);

    /**
     * 记录任务开始运行
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
     * @param taskId
     * @return void
     **/
    void recordEndTask(Long taskId, String description);

}
