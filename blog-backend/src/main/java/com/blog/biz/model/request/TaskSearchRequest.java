package com.blog.biz.model.request;

import com.blog.biz.enums.TaskStatus;
import com.blog.common.base.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskSearchRequest extends PageRequest {

    @Serial
    private static final long serialVersionUID = -6046035623471194548L;

    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "任务状态")
    private TaskStatus status;

}
