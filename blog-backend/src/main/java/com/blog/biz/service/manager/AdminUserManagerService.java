package com.blog.biz.service.manager;

import com.blog.biz.model.request.AdminUserRequest;
import com.blog.biz.model.response.AdminUserResponse;

import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface AdminUserManagerService {

	/**
	 * 查询所有管理员用户信息
	 * @param
	 * @return List<UserResponse>
	 **/
	List<AdminUserResponse> list();

	/**
	 * 保存管理员用户信息
	 * @param request
	 * @return void
	 **/
	void saveOrUpdate(AdminUserRequest request);

}
