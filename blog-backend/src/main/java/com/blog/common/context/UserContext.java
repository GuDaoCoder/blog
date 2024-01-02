package com.blog.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.blog.common.domain.UserDetail;

import java.util.Optional;

/**
 * @author zouzhangpeng
 * @desc
 */
public class UserContext {

    private static final TransmittableThreadLocal<UserDetail> local = new TransmittableThreadLocal<>();

    public static void set(UserDetail userDetail) {
        local.set(userDetail);
    }

    public static Optional<UserDetail> get() {
        return Optional.ofNullable(local.get());
    }

    public static void remove() {
        local.remove();
    }
}
