package com.blog.biz.service.impl;

import com.blog.common.base.service.UserDetailLoadService;
import com.blog.common.domain.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailLoadService {

    @Override
    public UserDetail load(String username) {
        return null;
    }
}
