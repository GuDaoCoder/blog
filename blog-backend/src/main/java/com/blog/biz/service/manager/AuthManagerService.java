package com.blog.biz.service.manager;

import com.blog.biz.model.request.LoginRequest;
import com.blog.biz.model.response.LoginResponse;

/**
 * @author zouzhangpeng
 * @desc 认证接口
 */
public interface AuthManagerService {

    /**
     * 登录
     * 
     * @param request LoginRequest
     * @return com.blog.biz.model.vo.LoginResponse
     */
    LoginResponse login(LoginRequest request);

    /**
     * 登出
     * 
     * @param
     */
    void logout();
}
