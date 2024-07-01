package com.blog.biz.service.crud.impl;

import com.blog.biz.model.entity.TaskEntity;
import com.blog.biz.mapper.TaskMapper;
import com.blog.biz.service.crud.TaskCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务信息表 服务实现类
 * </p>
 *
 * @author zane.zou
 * @since 2024-06-28
 */
@Slf4j
@Service
public class TaskCrudServiceImpl extends BaseCrudServiceImpl<TaskMapper, TaskEntity> implements TaskCrudService {

}
