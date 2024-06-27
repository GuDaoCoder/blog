package com.blog.biz.mapper;

import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.model.entity.custom.CategoryPostCountEntity;
import com.blog.common.base.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface PostMapper extends IBaseMapper<PostEntity> {

    @Select("""
            <script>
            select category_id,count(*) as post_count from t_post
            where category_id in
            <foreach item="item" index="index" collection="categoryIds" open="(" separator="," close=")" >
             #{item}
            </foreach>
            group by category_id
            </script>""")
    List<CategoryPostCountEntity> getCategoryPostCountEntity(
            @Param("categoryIds") Collection<? extends Serializable> categoryIds);

}
