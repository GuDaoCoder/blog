package com.blog.biz.convert;

import com.blog.biz.model.context.SearchPostContext;
import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.model.request.PostPortalSearchRequest;
import com.blog.biz.model.request.PostSearchRequest;
import com.blog.biz.model.response.PostDetailResponse;
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

    PostResponse toResponse(PostEntity entity);

    PostDetailResponse toDetailResponse(PostResponse postResponse);

    SearchPostContext toPageContext(PostSearchRequest request);

    SearchPostContext toPageContext(PostPortalSearchRequest request);

}
