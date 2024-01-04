package com.blog.biz.service.crud;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blog.biz.model.entity.TagEntity;
import com.blog.common.base.service.BaseCrudService;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface TagCrudService extends BaseCrudService<TagEntity> {

    /**
     * 查询最新的一个标签
     * 
     * @param
     * @return java.util.Optional<com.blog.biz.model.entity.TagEntity>
     */
    Optional<TagEntity> findLatest();

    /**
     * 新增或更新标签
     * 
     * @param entity TagEntity
     * @return java.lang.Long
     */
    Long saveOrUpdate(TagEntity entity);

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
     * @param pageable Pageable
     * @return org.springframework.data.domain.Page<com.blog.biz.model.entity.TagEntity>
     */
    Page<TagEntity> page(String tagName, Pageable pageable);

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
