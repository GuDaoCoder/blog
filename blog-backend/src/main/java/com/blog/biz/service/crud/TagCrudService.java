package com.blog.biz.service.crud;

import java.util.List;
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
     * 根据名称查询标签
     *
     * @param tagName String
     * @return java.util.Optional<com.blog.biz.model.entity.TagEntity>
     */
    Optional<TagEntity> findByTagName(String tagName);

    /**
     * 分页查询标签
     *
     * @param tagName  String
     * @param pageable IPage<TagEntity>
     * @return IPage<TagEntity>
     */
    IPage<TagEntity> page(String tagName, IPage<TagEntity> pageable);

    List<TagEntity> findAllByTagNames(List<String> tagNames);

    /**
     * 查询所有标签
     *
     * @param
     * @return List<TagEntity>
     **/
    List<TagEntity> findAll();
}
