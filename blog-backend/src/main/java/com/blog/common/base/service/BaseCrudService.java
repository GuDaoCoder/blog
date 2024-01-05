package com.blog.common.base.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.blog.common.base.entity.BaseEntity;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface BaseCrudService<Entity extends BaseEntity> {

    /**
     * 根据主键查询数据
     * 
     * @param id Long
     * @return java.util.Optional<Entity>
     */
    Optional<Entity> findOneById(Long id);

    /**
     * 根据主键查询数据，不存在则抛出异常
     * 
     * @param id Long
     * @return Entity
     */
    Entity findOneByIdOrThrow(Long id);

    /**
     * 根据多个Id查询数据
     * 
     * @param ids Long>
     * @return java.util.List<Entity>
     */
    List<Entity> findAllByIds(List<Long> ids);

    /**
     * 新增或更新数据
     * 
     * @param entity Entity
     */
    void save(Entity entity);

    /**
     * 批量新增或更新数据
     * 
     * @param entities Collection<Entity>
     */
    void saveAll(Collection<Entity> entities);

    /**
     * 查询所有数据
     * 
     * @param
     * @return java.util.List<Entity>
     */
    List<Entity> findAll();

}
