package com.blog.biz.convert;

import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.model.request.CreateTagRequest;
import com.blog.biz.model.request.UpdateTagRequest;
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

    TagEntity toEntity(CreateTagRequest request);

    TagEntity toEntity(UpdateTagRequest request);

    TagResponse toResponse(TagEntity entity);

}
