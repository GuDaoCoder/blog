package com.blog.common.context;

import java.util.Optional;

import com.alibaba.ttl.TransmittableThreadLocal;

import lombok.Data;

/**
 * 请求上下文
 *
 * @author zouzhangpeng
 */
public class RequestContext {

    private static final TransmittableThreadLocal<Info> local = new TransmittableThreadLocal<>();

    public static void set(Info info) {
        local.set(info);
    }

    public static Info get() {
        return local.get();
    }

    public static void remove() {
        local.remove();
    }

    public static String getRequestId() {
        return Optional.ofNullable(get()).map(Info::getRequestId).orElse(null);
    }
    @Data
    public static class Info {

        /**
         * 请求ID
         */
        private String requestId;

    }
}
