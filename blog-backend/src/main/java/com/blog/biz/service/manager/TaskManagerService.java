package com.blog.biz.service.manager;

import com.blog.biz.enums.TaskStatus;
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
     * 启动新的任务
     * @param taskName
     * @return Long
     **/
    Long startNewTask(String taskName);

    /**
     * 结束任务
     * @param taskId
     * @param status
     * @param log
     * @return void
     **/
    void endTask(Long taskId, TaskStatus status, String log);

}
