package com.blog.biz.service.manager;

import com.blog.biz.enums.OverviewType;

public interface OverviewStatisticService {

    OverviewType overviewType();

    Long overviewCount();
}
