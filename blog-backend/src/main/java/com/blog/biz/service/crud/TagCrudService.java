package com.blog.biz.service.crud;

import java.util.Optional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.biz.model.entity.TagEntity;
import com.blog.common.base.service.IBaseCrudService;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface TagCrudService extends IBaseCrudService<TagEntity> {

    /**
     * 查询最新的一个标签
     * 
     * @param
     * @return java.util.Optional<com.blog.biz.model.entity.TagEntity>
     */
    Optional<TagEntity> findLatest();

    /**
     * 根据名称查询标签
     * 
     * @param tagName String
     * @return java.util.Optional<com.blog.biz.model.entity.TagEntity>
     */
    Optional<TagEntity> findByTagName(String tagName);

    /**
     * 分页查询标签
     * 
     * @param tagName String
     * @param pageable IPage<TagEntity>
     * @return IPage<TagEntity>
     */
    IPage<TagEntity> page(String tagName, IPage<TagEntity> pageable);

    /**
     * 根据顺序编号查询前一个标签
     * 
     * @param orderNo Integer
     * @return java.util.Optional<com.blog.biz.model.entity.TagEntity>
     */
    Optional<TagEntity> findPrevious(Integer orderNo);

    /**
     * 根据顺序编号查询后一个标签
     * 
     * @param orderNo Integer
     * @return java.util.Optional<com.blog.biz.model.entity.TagEntity>
     */
    Optional<TagEntity> findLatter(Integer orderNo);

}
