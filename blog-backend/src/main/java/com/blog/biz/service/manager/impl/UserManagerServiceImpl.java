package com.blog.biz.service.manager.impl;

import com.blog.biz.constant.RedisKeyConstant;
import com.blog.biz.convert.UserConverter;
import com.blog.biz.service.crud.UserCrudService;
import com.blog.biz.service.manager.UserManagerService;
import com.blog.common.base.service.UserDetailLoadService;
import com.blog.common.domain.UserDetail;
import com.blog.common.exception.BusinessException;
import com.blog.common.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author zouzhangpeng
 * @desc
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class UserManagerServiceImpl implements UserManagerService, UserDetailLoadService {

    private final UserCrudService userCrudService;

    @Override
    public UserDetail load(String username) {
        UserDetail userDetail = RedisUtil.getCacheObject(RedisKeyConstant.LOGIN_USER + username);
        if (Objects.isNull(userDetail)) {
            userDetail = userCrudService.findByUsername(username).map(UserConverter.INSTANCE::toUserDetail)
                .orElseThrow(() -> new BusinessException("获取用户信息失败"));
        }
        return userDetail;
    }
}
