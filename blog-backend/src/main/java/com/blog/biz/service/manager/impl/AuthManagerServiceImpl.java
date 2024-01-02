package com.blog.biz.service.manager.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.blog.biz.convert.UserConverter;
import com.blog.biz.event.publish.LoginSuccessEventPublisher;
import com.blog.biz.model.entity.UserEntity;
import com.blog.biz.model.request.LoginRequest;
import com.blog.biz.model.response.LoginResponse;
import com.blog.biz.service.crud.UserCrudService;
import com.blog.biz.service.manager.AuthManagerService;
import com.blog.common.domain.UserDetail;
import com.blog.common.exception.InvalidCredentialsException;
import com.blog.common.property.SecurityProperty;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;

/**
 * @author zouzhangpeng
 * @desc
 */
@RequiredArgsConstructor
@Service
public class AuthManagerServiceImpl implements AuthManagerService {

    private final UserCrudService userCrudService;

    private final SecurityProperty securityProperty;

    private final LoginSuccessEventPublisher loginSuccessEventPublisher;

    @Override
    public LoginResponse login(LoginRequest request) {
        UserEntity userEntity =
            userCrudService.findByUsername(request.getUsername()).orElseThrow(() -> new InvalidCredentialsException());
        if (matchPassword(request.getPassword(), userEntity.getPassword())) {
            StpUtil.login(userEntity.getUsername());
        } else {
            throw new InvalidCredentialsException();
        }
        // 发送用户登录事件
        UserDetail userDetail = UserConverter.INSTANCE.toUserDetail(userEntity);
        loginSuccessEventPublisher.publish(userDetail);
        return new LoginResponse(StpUtil.getTokenValue());
    }

    /**
     * 判断明文密码是否加密密码是否匹配
     * 
     * @param password String
     * @param encodePassword String
     * @return boolean
     */
    private boolean matchPassword(String password, String encodePassword) {
        return StringUtils.equals(password,
            SaSecureUtil.rsaDecryptByPrivate(securityProperty.getRsa().getPrivateKey(), encodePassword));
    }
}
