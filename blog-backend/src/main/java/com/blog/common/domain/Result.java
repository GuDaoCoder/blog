package com.blog.common.domain;

import com.blog.common.util.DateTimeUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc 请求返回统一包装类
 */
@Data
public class Result<T> {

    @Schema(description = "请求Id")
    private String requestId;

    @Schema(description = "时间戳")
    private Long timestamp;

    @Schema(description = "错误信息")
    private String errorMsg;

    @Schema(description = "返回数据")
    private T data;

    public Result(T data, String errorMsg) {
        // todo: 从threadLocal中获取
        this.requestId = null;
        this.timestamp = DateTimeUtil.timestamp();
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(String errorMsg) {
        return new Result<>(null, errorMsg);
    }
}
