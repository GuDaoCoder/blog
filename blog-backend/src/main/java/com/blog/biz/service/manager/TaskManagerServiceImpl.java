package com.blog.biz.service.manager;

import com.blog.biz.enums.TaskStatus;
import com.blog.biz.model.entity.TaskEntity;
import com.blog.biz.service.crud.TaskCrudService;
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
        taskCrudService.getOptById(taskId)
                .ifPresent(taskEntity -> {
                    taskEntity.setEndDateTime(LocalDateTime.now())
                            .setStatus(TaskStatus.FAIL).setDescription(description)
                            .setErrorMsg(ExceptionUtils.getRootCauseMessage(ex));
                    taskCrudService.updateById(taskEntity);
                });
    }

    @Override
    public void recordEndTask(Long taskId, String description) {
        if (taskId == null) {
            return;
        }
        taskCrudService.getOptById(taskId)
                .ifPresent(taskEntity -> {
                    taskEntity.setEndDateTime(LocalDateTime.now())
                            .setDescription(description)
                            .setStatus(TaskStatus.SUCCESS);
                    taskCrudService.updateById(taskEntity);
                });
    }
}
