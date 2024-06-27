package com.blog.biz.convert;

import com.blog.biz.model.entity.GitRepositoryEntity;
import com.blog.biz.model.request.GitRepositoryRequest;
import com.blog.biz.model.response.GitRepositoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GitRepositoryConverter {

    GitRepositoryConverter INSTANCE = Mappers.getMapper(GitRepositoryConverter.class);

    GitRepositoryResponse toResponse(GitRepositoryEntity entity);

    GitRepositoryEntity toEntity(GitRepositoryRequest request);

}
