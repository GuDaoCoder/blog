package com.blog.biz.convert;

import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.request.TagRequest;
import com.blog.biz.model.response.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zouzhangpeng
 * @desc
 */
@Mapper
public interface TagConverter {

    TagConverter INSTANCE = Mappers.getMapper(TagConverter.class);

    TagEntity toEntity(TagRequest request);

    TagResponse toResponse(TagEntity entity);

}
