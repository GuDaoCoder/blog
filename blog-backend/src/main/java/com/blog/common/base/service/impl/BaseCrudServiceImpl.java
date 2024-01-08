package com.blog.common.base.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.base.entity.BaseEntity;
import com.blog.common.base.request.PageRequest;
import com.blog.common.base.service.IBaseCrudService;
import com.blog.common.exception.DataNotFoundException;

/**
 * @author zouzhangpeng
 * @desc
 */
public class BaseCrudServiceImpl<Mapper extends BaseMapper<Entity>, Entity extends BaseEntity>
    extends ServiceImpl<Mapper, Entity> implements IBaseCrudService<Entity> {

    @Override
    public Entity getOneOrThrow(Long id) {
        return this.getOptById(id).orElseThrow(() -> new DataNotFoundException());
    }

    protected String limitOneExpression() {
        return "limit 1";
    }

    protected IPage<Entity> pageable(PageRequest pageRequest) {
        IPage<Entity> pageable = new Page<>();
        pageable.setCurrent(pageRequest.getPageNumber());
        pageable.setSize(pageRequest.getPageSize());
        return pageable;
    }


}