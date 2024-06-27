package com.blog.biz.convert;

import com.blog.biz.model.entity.AdminUserEntity;
import com.blog.biz.model.request.AdminUserRequest;
import com.blog.biz.model.response.AdminUserResponse;
import com.blog.common.domain.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zouzhangpeng
 * @desc
 */
@Mapper
public interface AdminUserConverter {

    AdminUserConverter INSTANCE = Mappers.getMapper(AdminUserConverter.class);

    UserDetail toUserDetail(AdminUserEntity entity);

    AdminUserResponse toUserResponse(AdminUserEntity entity);

    AdminUserEntity toEntity(AdminUserRequest request);

}
