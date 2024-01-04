package com.blog.biz.convert;

import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.response.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.blog.biz.model.entity.TagEntity;

/**
 * @author zouzhangpeng
 * @desc
 */
@Mapper
public interface TagConverter {

    TagConverter INSTANCE = Mappers.getMapper(TagConverter.class);

    TagEntity toEntity(CreateTagRequest request);

    TagResponse toResponse(TagEntity entity);
}
