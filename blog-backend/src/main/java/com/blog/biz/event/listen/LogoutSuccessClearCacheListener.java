package com.blog.biz.event.listen;

import com.blog.biz.constant.RedisKeyConstant;
import com.blog.biz.event.model.LogoutSuccessEvent;
import com.blog.common.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author zouzhangpeng
 * @desc 用户登出后清除缓存
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class LogoutSuccessClearCacheListener implements ApplicationListener<LogoutSuccessEvent> {

	@Override
	public void onApplicationEvent(LogoutSuccessEvent event) {
		log.info(">>>>>>>>>>用户登出后清除缓存");
		RedisUtil.deleteObject(RedisKeyConstant.LOGIN_USER + event.getUserDetail().getUsername());
	}

}
