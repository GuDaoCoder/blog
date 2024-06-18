package com.blog.biz.service.crud.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.biz.mapper.AdminUserMapper;
import com.blog.biz.model.entity.AdminUserEntity;
import com.blog.biz.service.crud.AdminUserCrudService;
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
public class AdminUserCrudServiceImpl extends BaseCrudServiceImpl<AdminUserMapper, AdminUserEntity> implements AdminUserCrudService {

    @Override
    public Optional<AdminUserEntity> findByUsername(String username) {
        LambdaQueryWrapper<AdminUserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AdminUserEntity::getUsername, username);
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }
}
