package com.blog.biz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.biz.enums.TaskStatus;
import com.blog.common.base.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.io.Serial;

/**
 * <p>
 * 任务信息表
 * </p>
 *
 * @author zane.zou
 * @since 2024-06-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_task")
public class TaskEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "task_id", type = IdType.AUTO)
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务状态
     */
    private TaskStatus status;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 任务开始时间
     */
    private LocalDateTime beginDateTime;

    /**
     * 任务结束时间
     */
    private LocalDateTime endDateTime;
}
