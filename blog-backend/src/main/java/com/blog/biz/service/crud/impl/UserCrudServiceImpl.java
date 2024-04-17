package com.blog.biz.service.crud.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.biz.mapper.UserMapper;
import com.blog.biz.model.entity.UserEntity;
import com.blog.biz.service.crud.UserCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserCrudServiceImpl extends BaseCrudServiceImpl<UserMapper, UserEntity> implements UserCrudService {

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserEntity::getUsername, username);
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }
}
