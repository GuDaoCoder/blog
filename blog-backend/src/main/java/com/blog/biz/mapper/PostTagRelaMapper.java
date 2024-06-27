package com.blog.biz.mapper;

import com.blog.biz.model.entity.PostTagRelaEntity;
import com.blog.biz.model.entity.custom.TagPostCountEntity;
import com.blog.common.base.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface PostTagRelaMapper extends IBaseMapper<PostTagRelaEntity> {

	@Select("""
			<script>
			select tag_id,count(*) as post_count from t_post_tag_rela
			where tag_id in
			<foreach item="item" index="index" collection="tagIds" open="(" separator="," close=")" >
			 #{item}
			</foreach>
			group by tag_id
			</script>""")
	List<TagPostCountEntity> getTagPostCountEntity(@Param("tagIds") List<Long> tagIds);

}
