package com.blog.biz.convert;

import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.model.request.CreatePostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zouzhangpeng
 * @desc
 */
@Mapper
public interface PostConverter {

    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

    PostEntity toEntity(CreatePostRequest request);
}
