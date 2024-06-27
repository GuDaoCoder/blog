package com.blog.biz.service.manager.impl;

import com.blog.biz.constant.RedisKeyConstant;
import com.blog.biz.convert.AdminUserConverter;
import com.blog.biz.model.entity.AdminUserEntity;
import com.blog.biz.model.request.AdminUserRequest;
import com.blog.biz.model.response.AdminUserResponse;
import com.blog.biz.service.crud.AdminUserCrudService;
import com.blog.biz.service.manager.AdminUserManagerService;
import com.blog.common.base.service.UserDetailLoadService;
import com.blog.common.domain.UserDetail;
import com.blog.common.exception.BusinessException;
import com.blog.common.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zouzhangpeng
 * @desc
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AdminUserManagerServiceImpl implements AdminUserManagerService, UserDetailLoadService {

	private final AdminUserCrudService adminUserCrudService;

	@Override
	public UserDetail load(String username) {
		UserDetail userDetail = RedisUtil.getCacheObject(RedisKeyConstant.LOGIN_USER + username);
		if (Objects.isNull(userDetail)) {
			userDetail = adminUserCrudService.findByUsername(username)
				.map(AdminUserConverter.INSTANCE::toUserDetail)
				.orElseThrow(() -> new BusinessException("获取用户信息失败"));
		}
		return userDetail;
	}

	@Override
	public List<AdminUserResponse> list() {
		return adminUserCrudService.list().stream().map(AdminUserConverter.INSTANCE::toUserResponse).toList();
	}

	@Override
	public void saveOrUpdate(AdminUserRequest request) {
		if (Objects.isNull(request.getUserId()) && adminUserCrudService.count() > 0) {
			throw new BusinessException("系统只能有一个管理员用户");
		}

		AdminUserEntity adminUserEntity = AdminUserConverter.INSTANCE.toEntity(request);
		adminUserCrudService.saveOrUpdate(adminUserEntity);
	}

}
