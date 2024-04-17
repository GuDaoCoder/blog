package com.blog.biz.event.model;

import com.blog.common.domain.UserDetail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author zouzhangpeng
 * @desc
 */
@Setter
@Getter
public class LogoutSuccessEvent extends ApplicationEvent {

    private UserDetail userDetail;

    public LogoutSuccessEvent(Object source) {
        super(source);
    }

    public LogoutSuccessEvent(Object source, UserDetail userDetail)    {
        super(source);
        this.userDetail = userDetail;
    }
}
