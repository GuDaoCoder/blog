package com.blog.biz.service.crud.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.biz.model.context.TaskSearchContext;
import com.blog.biz.model.entity.TaskEntity;
import com.blog.biz.mapper.TaskMapper;
import com.blog.biz.service.crud.TaskCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    @Override
    public IPage<TaskEntity> page(TaskSearchContext context) {
        LambdaQueryWrapper<TaskEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(context.getTaskName()), TaskEntity::getTaskName, context.getTaskName())
            .eq(Objects.nonNull(context.getStatus()), TaskEntity::getStatus, context.getStatus());
        return baseMapper.selectPage(context.getPageable(), queryWrapper);
    }

}
