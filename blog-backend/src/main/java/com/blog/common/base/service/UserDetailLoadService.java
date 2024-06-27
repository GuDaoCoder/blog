package com.blog.common.base.service;

import com.blog.common.domain.UserDetail;

/**
 * @author zouzhangpeng
 * @desc 用户详情接口
 */
public interface UserDetailLoadService {

	/**
	 * 根据用户名称查询用户详情
	 * @param username String
	 * @return com.blog.common.domain.UserDetail
	 */
	UserDetail load(String username);

}
