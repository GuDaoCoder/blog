package com.blog.biz.service.crud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.model.context.TaskSearchContext;
import com.blog.biz.model.entity.TaskEntity;
import com.blog.common.base.service.IBaseCrudService;

/**
 * <p>
 * 任务信息表 服务类
 * </p>
 *
 * @author zane.zou
 * @since 2024-06-28
 */
public interface TaskCrudService extends IBaseCrudService<TaskEntity> {

    /**
     * 分页查询
     * @param context
     * @return
     */
    IPage<TaskEntity> page(TaskSearchContext context);

}
