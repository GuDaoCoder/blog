package com.blog.common.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.base.entity.BaseEntity;
import com.blog.common.base.request.PageRequest;
import com.blog.common.base.response.SearchResponse;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

/**
 * @author zouzhangpeng
 * @desc
 */
public class PageUtil {

    public static <Entity extends BaseEntity> IPage<Entity> pageable(PageRequest pageRequest) {
        Long pageNumber = Optional.ofNullable(pageRequest.getPageNumber()).orElse(1L);
        Long pageSize = Optional.ofNullable(pageRequest.getPageSize()).orElse(10L);
        IPage<Entity> pageable = new Page<>();
        pageable.setCurrent(pageNumber);
        pageable.setSize(pageSize);
        return pageable;
    }

    public static <Response, Entity extends BaseEntity> SearchResponse<Response> toResult(IPage<Entity> page,
                                                                                          Function<Entity, Response> converter) {
        SearchResponse<Response> searchResponse = new SearchResponse<>();
        searchResponse.setPageNumber(page.getCurrent()).setPageSize(page.getSize()).setTotal(page.getTotal());
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            searchResponse.setItems(page.getRecords().stream().map(converter).collect(Collectors.toList()));
        }
        return searchResponse;
    }

    public static <Response, Entity extends BaseEntity> SearchResponse<Response> result(IPage<Entity> page,
                                                                                        List<Response> responses) {
        SearchResponse<Response> searchResponse = new SearchResponse<>();
        searchResponse.setPageNumber(page.getCurrent())
                .setPageSize(page.getSize())
                .setTotal(page.getTotal())
                .setItems(responses);
        return searchResponse;
    }
}
