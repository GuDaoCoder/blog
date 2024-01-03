package com.blog.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.blog.common.domain.UserDetail;

/**
 * @author zouzhangpeng
 * @desc
 */
public class UserContext {

    private static final TransmittableThreadLocal<UserDetail> local = new TransmittableThreadLocal<>();

    public static void set(UserDetail userDetail) {
        local.set(userDetail);
    }

    public static UserDetail get() {
        return local.get();
    }

    public static void remove() {
        local.remove();
    }
}
