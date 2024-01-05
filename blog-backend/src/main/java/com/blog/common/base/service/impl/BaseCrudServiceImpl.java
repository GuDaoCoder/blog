package com.blog.common.base.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.common.base.entity.BaseEntity;
import com.blog.common.base.service.BaseCrudService;
import com.blog.common.exception.DataNotFoundException;

/**
 * @author zouzhangpeng
 * @desc
 */
public class BaseCrudServiceImpl<Entity extends BaseEntity, Repository extends JpaRepository<Entity, Long>>
    implements BaseCrudService<Entity> {

    protected final Repository repository;

    public BaseCrudServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Entity> findOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Entity findOneByIdOrThrow(Long id) {
        return findOneById(id).orElseThrow(() -> new DataNotFoundException());
    }

    @Override
    public List<Entity> findAllByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<Entity> list = new ArrayList<>();
        repository.findAllById(ids).forEach(list::add);
        return list;
    }

    @Override
    public void save(Entity entity) {
        repository.save(entity);
    }

    @Override
    public void saveAll(Collection<Entity> entities) {
        repository.saveAll(entities);
    }

    @Override
    public List<Entity> findAll() {
        return repository.findAll();
    }
}