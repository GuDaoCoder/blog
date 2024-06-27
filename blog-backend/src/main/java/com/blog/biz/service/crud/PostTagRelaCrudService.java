package com.blog.biz.service.crud;

import com.blog.biz.model.entity.PostTagRelaEntity;
import com.blog.biz.model.entity.custom.TagPostCountEntity;
import com.blog.common.base.service.IBaseCrudService;

import java.util.List;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface PostTagRelaCrudService extends IBaseCrudService<PostTagRelaEntity> {

	/**
	 * 查询标签是否被使用
	 * @param tagId Long
	 * @return boolean
	 */
	boolean tagUsed(Long tagId);

	List<TagPostCountEntity> getTagPostCountEntity(List<Long> tagIds);

	List<PostTagRelaEntity> findAllPostATagRelaByPostId(Long postId);

}
