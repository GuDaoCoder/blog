package com.blog.biz.convert;

import com.blog.biz.model.context.TaskSearchContext;
import com.blog.biz.model.entity.TaskEntity;
import com.blog.biz.model.request.TaskSearchRequest;
import com.blog.biz.model.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskConverter {

    TaskConverter INSTANCE = Mappers.getMapper(TaskConverter.class);

    TaskSearchContext toSearchContext(TaskSearchRequest request);

    TaskResponse toResponse(TaskEntity entity);

}
