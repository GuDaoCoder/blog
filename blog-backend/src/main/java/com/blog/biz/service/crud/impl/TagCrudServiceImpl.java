package com.blog.biz.service.crud.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.biz.model.entity.QTagEntity;
import com.blog.biz.model.entity.TagEntity;
import com.blog.biz.repository.TagRepository;
import com.blog.biz.service.crud.TagCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;
import com.blog.common.util.JpaUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class TagCrudServiceImpl extends BaseCrudServiceImpl<TagEntity, TagRepository> implements TagCrudService {

    public TagCrudServiceImpl(TagRepository repository) {
        super(repository);
    }

    @Override
    public Optional<TagEntity> findLatest() {
        return JpaUtil.query(QTagEntity.tagEntity).order(QTagEntity.tagEntity.orderNo.desc()).fetchOne();
    }

    @Override
    public Long saveOrUpdate(TagEntity entity) {
        return repository.save(entity).getTagId();
    }

    @Override
    public Optional<TagEntity> findByTagName(String tagName) {
        return JpaUtil.query(QTagEntity.tagEntity).eq(QTagEntity.tagEntity.tagName, tagName).fetchOne();
    }

    @Override
    public Page<TagEntity> page(String tagName, Pageable pageable) {
        return JpaUtil.query(QTagEntity.tagEntity)
            .like(StringUtils.isNotBlank(tagName), QTagEntity.tagEntity.tagName, tagName)
            .order(QTagEntity.tagEntity.orderNo.desc()).page(pageable);
    }

    @Override
    public Optional<TagEntity> findPrevious(Integer orderNo) {
        return JpaUtil.query(QTagEntity.tagEntity).gt(QTagEntity.tagEntity.orderNo, orderNo)
            .order(QTagEntity.tagEntity.orderNo.asc()).fetchOne();
    }

    @Override
    public Optional<TagEntity> findLatter(Integer orderNo) {
        return JpaUtil.query(QTagEntity.tagEntity).lt(QTagEntity.tagEntity.orderNo, orderNo)
            .order(QTagEntity.tagEntity.orderNo.desc()).fetchOne();
    }
}
