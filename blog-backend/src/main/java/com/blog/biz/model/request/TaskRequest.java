package com.blog.biz.model.request;

import com.blog.common.base.request.CommonRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.base.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "任务信息表")
public class TaskRequest extends CommonRequest {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long taskId;

    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "任务状态")
    private String status;

    @Schema(description = "任务描述")
    private String description;

    @Schema(description = "异常信息")
    private String errorMsg;

    @Schema(description = "任务开始时间")
    private LocalDateTime beginDateTime;

    @Schema(description = "任务结束时间")
    private LocalDateTime endDateTime;

}
