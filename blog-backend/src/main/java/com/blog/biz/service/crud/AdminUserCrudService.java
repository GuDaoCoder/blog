package com.blog.biz.service.crud;

import com.blog.biz.model.entity.AdminUserEntity;
import com.blog.common.base.service.IBaseCrudService;

import java.util.Optional;

/**
 * @author zouzhangpeng
 * @desc 管理员CRUD
 */
public interface AdminUserCrudService extends IBaseCrudService<AdminUserEntity> {

    /**
     * 根据用户名查询用户信息
     * @param username String
     * @return java.util.Optional<com.blog.biz.model.entity.UserEntity>
     */
    Optional<AdminUserEntity> findByUsername(String username);

}
