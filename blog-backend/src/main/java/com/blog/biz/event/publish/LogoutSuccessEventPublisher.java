package com.blog.biz.event.publish;

import com.blog.biz.event.model.LogoutSuccessEvent;
import com.blog.common.domain.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class LogoutSuccessEventPublisher implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void publish(UserDetail userDetail) {
		LogoutSuccessEvent event = new LogoutSuccessEvent(this, userDetail);
		applicationEventPublisher.publishEvent(event);
	}

}
