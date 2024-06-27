package com.blog.biz.event.listen;

import com.blog.biz.event.model.LoginSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author zouzhangpeng
 * @desc 用户登录后记录审计日志
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class LoginSuccessSaveLogListener implements ApplicationListener<LoginSuccessEvent> {

	@Override
	public void onApplicationEvent(LoginSuccessEvent event) {
		log.info(">>>>>>>>>>用户登录后记录审计日志");
	}

}
