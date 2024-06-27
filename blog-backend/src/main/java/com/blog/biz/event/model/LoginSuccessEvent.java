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
public class LoginSuccessEvent extends ApplicationEvent {

    private UserDetail userDetail;

    public LoginSuccessEvent(Object source) {
        super(source);
    }

    public LoginSuccessEvent(Object source, UserDetail userDetail) {
        super(source);
        this.userDetail = userDetail;
    }

}
