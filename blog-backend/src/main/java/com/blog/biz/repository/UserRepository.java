package com.blog.biz.repository;

import com.blog.biz.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zouzhangpeng
 * @desc
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {

    /**
     * 根据用户名查询管理员信息
     * 
     * @param username String
     * @return com.blog.biz.model.entity.UserEntity
     */
    UserEntity findByUsername(String username);
}
