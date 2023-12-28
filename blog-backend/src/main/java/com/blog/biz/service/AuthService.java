package com.blog.biz.service;

import com.blog.biz.model.dto.LoginDTO;
import com.blog.biz.model.vo.LoginVO;

/**
 * @author zouzhangpeng
 * @desc 认证接口
 */
public interface AuthService {

    /**
     * 登录
     * 
     * @param dto LoginDTO
     * @return com.blog.biz.model.vo.LoginVO
     */
    LoginVO login(LoginDTO dto);
}
