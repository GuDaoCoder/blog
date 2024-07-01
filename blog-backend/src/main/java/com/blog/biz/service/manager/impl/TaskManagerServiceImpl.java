package com.blog.biz.service.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.convert.TaskConverter;
import com.blog.biz.enums.TaskStatus;
import com.blog.biz.model.context.TaskSearchContext;
import com.blog.biz.model.entity.TaskEntity;
import com.blog.biz.model.request.TaskSearchRequest;
import com.blog.biz.model.response.TaskResponse;
import com.blog.biz.service.crud.TaskCrudService;
import com.blog.biz.service.manager.TaskManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class TaskManagerServiceImpl implements TaskManagerService {

    private final TaskCrudService taskCrudService;

    @Override
    public PageResponse<TaskResponse> search(TaskSearchRequest request) {
        TaskSearchContext searchContext = TaskConverter.INSTANCE.toSearchContext(request);
        searchContext.setPageable(PageUtil.pageable(request));
        IPage<TaskEntity> page = taskCrudService.page(searchContext);
        return PageUtil.toResult(page, TaskConverter.INSTANCE::toResponse);
    }

    @Override
    public Long recordRunTask(String taskName, String description) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName(taskName)
            .setStatus(TaskStatus.RUNNING)
            .setDescription(description)
            .setBeginDateTime(LocalDateTime.now());
        taskCrudService.save(taskEntity);
        return taskEntity.getTaskId();
    }

    @Override
    public void recordFailTask(Long taskId, Throwable ex, String description) {
        if (taskId == null) {
            return;
        }
        taskCrudService.getOptById(taskId).ifPresent(taskEntity -> {
            taskEntity.setEndDateTime(LocalDateTime.now())
                .setStatus(TaskStatus.FAIL)
                .setDescription(description)
                .setErrorMsg(ExceptionUtils.getRootCauseMessage(ex));
            taskCrudService.updateById(taskEntity);
        });
    }

    @Override
    public void recordEndTask(Long taskId, String description) {
        if (taskId == null) {
            return;
        }
        taskCrudService.getOptById(taskId).ifPresent(taskEntity -> {
            taskEntity.setEndDateTime(LocalDateTime.now()).setDescription(description).setStatus(TaskStatus.SUCCESS);
            taskCrudService.updateById(taskEntity);
        });
    }

}
