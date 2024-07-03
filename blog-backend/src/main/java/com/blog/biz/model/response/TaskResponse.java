package com.blog.biz.model.response;

import com.blog.common.base.response.CommonResponse;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.base.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class TaskResponse extends CommonResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long taskId;

    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "任务状态")
    private String status;

    @Schema(description = "日志信息")
    private String log;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "任务开始时间")
    private LocalDateTime beginDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "任务结束时间")
    private LocalDateTime endDateTime;

}
