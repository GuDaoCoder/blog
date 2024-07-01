package com.blog.biz.model.context;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.enums.TaskStatus;
import com.blog.biz.model.entity.TaskEntity;
import com.blog.common.base.context.BasePageContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskSearchContext extends BasePageContext<TaskEntity> {

    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "任务状态")
    private TaskStatus status;

    public TaskSearchContext(IPage<TaskEntity> pageable) {
        super(pageable);
    }

}
