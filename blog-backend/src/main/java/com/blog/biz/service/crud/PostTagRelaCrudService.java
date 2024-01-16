package com.blog.biz.service.crud;

import com.blog.biz.model.entity.PostTagRelaEntity;
import com.blog.common.base.service.IBaseCrudService;

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
}
