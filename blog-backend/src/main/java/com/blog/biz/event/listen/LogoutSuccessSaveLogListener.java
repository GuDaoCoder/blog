package com.blog.biz.event.listen;

import com.blog.biz.event.model.LogoutSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author zouzhangpeng
 * @desc 用户登出后记录日志
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class LogoutSuccessSaveLogListener implements ApplicationListener<LogoutSuccessEvent> {

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        log.info(">>>>>>>>>>用户登出后记录日志");
    }
}
