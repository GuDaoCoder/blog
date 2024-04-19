package com.blog.biz.service.manager.impl;

import com.blog.biz.model.response.QuantityStatisticsResponse;
import com.blog.biz.service.manager.OverviewStatisticService;
import com.blog.biz.service.manager.StatisticManagerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class StatisticManagerServiceImpl implements StatisticManagerService {

    private final List<OverviewStatisticService> overviewStatisticServices;

    @Qualifier("threadPoolTaskExecutor")
    private final Executor executor;

    @SneakyThrows
    @Override
    public List<QuantityStatisticsResponse> statisticOverview() {
        List<CompletableFuture<QuantityStatisticsResponse>> tasks = new ArrayList<>();
        if (CollectionUtils.isEmpty(overviewStatisticServices)) {
            return new ArrayList<>();
        }
        for (OverviewStatisticService service : overviewStatisticServices) {
            CompletableFuture<QuantityStatisticsResponse> task = CompletableFuture.supplyAsync(() ->
                    new QuantityStatisticsResponse(service.overviewType().name(),service.overviewType().getLabel(), service.overviewCount()), executor);
            tasks.add(task);
        }

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));

        CompletableFuture<List<QuantityStatisticsResponse>> allResults = allTasks.thenApply(v ->
                tasks.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );

        return allResults.get().stream().filter(Objects::nonNull).toList();
    }
}
