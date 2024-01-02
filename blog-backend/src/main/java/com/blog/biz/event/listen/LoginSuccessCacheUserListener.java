package com.blog.biz.event.listen;

import java.time.Duration;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.blog.biz.constant.RedisKeyConstant;
import com.blog.biz.event.model.LoginSuccessEvent;
import com.blog.common.property.SaTokenProperty;
import com.blog.common.redis.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc 用户登录后缓存当前用户信息
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class LoginSuccessCacheUserListener implements ApplicationListener<LoginSuccessEvent> {

    private final SaTokenProperty saTokenProperty;

    @Override
    public void onApplicationEvent(LoginSuccessEvent event) {
        log.info(">>>>>>>>>>用户登录后缓存当前用户信息");
        RedisUtil.setCacheObject(RedisKeyConstant.LOGIN_USER + event.getUserDetail().getUsername(),
            event.getUserDetail(), Duration.ofSeconds(saTokenProperty.getTimeout()));
    }
}
