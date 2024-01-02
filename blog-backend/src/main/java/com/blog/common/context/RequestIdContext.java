package com.blog.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Optional;

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

    public static Optional<String> get() {
        return Optional.ofNullable(local.get());
    }

    public static void remove() {
        local.remove();
    }

}
