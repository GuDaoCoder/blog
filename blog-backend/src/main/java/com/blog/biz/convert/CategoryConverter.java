package com.blog.biz.convert;

import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.request.UpdateCategoryRequest;
import com.blog.biz.model.response.CategoryResponse;
import com.blog.biz.model.response.CategoryTreeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zouzhangpeng
 * @desc
 */
@Mapper
public interface CategoryConverter {

    CategoryConverter INSTANCE = Mappers.getMapper(CategoryConverter.class);

    CategoryEntity toEntity(CreateCategoryRequest request);

    CategoryEntity toEntity(UpdateCategoryRequest request);

    CategoryResponse toResponse(CategoryEntity entity);

    CategoryTreeResponse toTreeResponse(CategoryResponse categoryResponse);

}
