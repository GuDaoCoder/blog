package com.blog.biz.convert;

import com.blog.biz.model.entity.UserEntity;
import com.blog.common.domain.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zouzhangpeng
 * @desc
 */
@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(  UserConverter.class);

    UserDetail toUserDetail(UserEntity entity );
}
