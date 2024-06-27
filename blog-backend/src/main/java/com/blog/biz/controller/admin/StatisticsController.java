package com.blog.biz.controller.admin;

import com.blog.biz.model.response.QuantityStatisticsResponse;
import com.blog.biz.service.manager.StatisticManagerService;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "数据统计")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/statistics")
public class StatisticsController {

    private final StatisticManagerService statisticManagerService;

    @Operation(summary = "统计总览")
    @GetMapping("/statisticOverview")
    public Result<List<QuantityStatisticsResponse>> statisticOverview() {
        return Result.success(statisticManagerService.statisticOverview());
    }

}
