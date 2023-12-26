package com.blog.common.util;

import org.slf4j.MDC;

import com.blog.common.context.RequestContext;

import java.util.Optional;
import java.util.UUID;

/**
 * @author zouzhangpeng
 */
public class MdcUtil {
    public static final String TRACE_ID = "TRACE_ID";

    public static void init() {
        MDC.put(TRACE_ID, Optional.ofNullable(RequestContext.getRequestId()).orElse(traceId()));
    }

    public static void clear() {
        MDC.clear();
    }

    public static String traceId() {
        return UUID.randomUUID().toString();
    }

}
