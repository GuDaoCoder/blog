package com.blog.biz.convert;

import com.blog.biz.model.context.SearchPostContext;
import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.request.SearchPostRequest;
import com.blog.biz.model.request.UpdatePostRequest;
import com.blog.biz.model.response.PagePostResponse;
import com.blog.biz.model.response.PostResponse;
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

    PostEntity toEntity(UpdatePostRequest request);

    PostResponse toResponse(PostEntity entity);

    SearchPostContext toPageContext(SearchPostRequest request);

}
