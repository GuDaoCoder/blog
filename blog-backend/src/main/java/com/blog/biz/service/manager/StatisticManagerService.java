package com.blog.biz.service.manager;

import com.blog.biz.model.response.QuantityStatisticsResponse;

import java.util.List;

public interface StatisticManagerService {

	/**
	 * 统计总览
	 * @param
	 * @return List<QuantityStatisticsResponse>
	 **/
	List<QuantityStatisticsResponse> statisticOverview();

}
