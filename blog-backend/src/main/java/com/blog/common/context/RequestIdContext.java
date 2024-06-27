package com.blog.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 请求上下文
 *
 * @author zouzhangpeng
 */
public class RequestIdContext {

	private static final TransmittableThreadLocal<String> local = new TransmittableThreadLocal<>();

	public static void set(String requestId) {
		local.set(requestId);
	}

	public static String get() {
		return local.get();
	}

	public static void remove() {
		local.remove();
	}

}
