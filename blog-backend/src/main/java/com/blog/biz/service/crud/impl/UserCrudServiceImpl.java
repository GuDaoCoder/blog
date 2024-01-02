package com.blog.biz.service.crud.impl;

import com.blog.biz.model.entity.UserEntity;
import com.blog.biz.repository.UserRepository;
import com.blog.biz.service.crud.UserCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserCrudServiceImpl implements UserCrudService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }
}
