package com.blog.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

/**
 * @author zouzhangpeng
 */
public class ServletUtil {

	public static final String REQUEST_ID = "RequestId";

	/**
	 * 获取前端传过来的RequestId
	 * @param request HttpServletRequest
	 * @return java.lang.String
	 */
	public static String getRequestId(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(REQUEST_ID)).orElse(UUID.randomUUID().toString());
	}

}
