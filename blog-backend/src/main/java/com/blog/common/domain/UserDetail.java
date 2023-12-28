package com.blog.common.domain;

import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc 用户详情
 */
@Data
public class UserDetail {

    private Long userId;

    private String username;

    private String nickName;

    private String password;

    private Boolean enabled;

    private Boolean unlocked;
}
