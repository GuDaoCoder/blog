package com.blog.biz.service.manager.impl;

import com.blog.biz.event.publish.LogoutSuccessEventPublisher;
import com.blog.common.context.UserContext;
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
import com.blog.common.properties.SecurityProperties;

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

    private final SecurityProperties securityProperties;

    private final LoginSuccessEventPublisher loginSuccessEventPublisher;

    private final LogoutSuccessEventPublisher logoutSuccessEventPublisher;

    @Override
    public LoginResponse login(LoginRequest request) {
        UserEntity userEntity =
            userCrudService.findByUsername(request.getUsername()).orElseThrow(() -> new InvalidCredentialsException());
        if (matchPassword(request.getPassword(), userEntity.getPassword())) {
            StpUtil.login(userEntity.getUsername());
        } else {
            throw new InvalidCredentialsException();
        }
        // 用户登录成功事件
        UserDetail userDetail = UserConverter.INSTANCE.toUserDetail(userEntity);
        loginSuccessEventPublisher.publish(userDetail);
        return new LoginResponse(StpUtil.getTokenValue());
    }

    @Override
    public void logout() {
        UserDetail userDetail = UserContext.get();
        // 登出
        StpUtil.logout(userDetail.getUsername());
        // 用户登出成功事件2
        logoutSuccessEventPublisher.publish(userDetail);
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
            SaSecureUtil.rsaDecryptByPrivate(securityProperties.getRsa().getPrivateKey(), encodePassword));
    }

    public static void main(String[] args) {
        System.out.println(SaSecureUtil.rsaEncryptByPublic("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/w2Y49BBaaCTSHnTxIdI0MGrhwTxFb2uNiHDjHw/w4bhXEy1X8WcPoqqNwqlHWZvrImvSprh3OtUS4GcWT/6UKQD/VA1iHhZ3m1xQ/CERpivZhOXRznWKGdpufCoYdjFZO7bqm4tGzyPkNGjblIQTRmWrG6lEwmE74EGVl/9q+QIDAQAB", "136415Zzp++"));
    }
}
