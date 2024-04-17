package com.blog.common.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.common.base.entity.BaseEntity;
import com.blog.common.base.service.IBaseCrudService;
import com.blog.common.exception.DataNotFoundException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zouzhangpeng
 * @desc
 */
public class BaseCrudServiceImpl<Mapper extends BaseMapper<Entity>, Entity extends BaseEntity>
    extends ServiceImpl<Mapper, Entity> implements IBaseCrudService<Entity> {

    @Override
    public Entity getOneOrThrow(Long id) {
        return this.getOptById(id).orElseThrow(DataNotFoundException::new);
    }

    @Override
    public <T> Optional<Entity> getByField(SFunction<Entity, T> function, T value) {
        LambdaQueryWrapper<Entity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(function, value);
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }

    @Override
    public <T> void removeByField(SFunction<Entity, T> function, T value) {
        LambdaUpdateWrapper<Entity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(function, value);
        baseMapper.delete(updateWrapper);
    }

    @Override
    public <T> List<Entity> listByFields(SFunction<Entity, T> function, List<T> values) {
        if (CollectionUtils.isEmpty(values)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<Entity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(function, values);
        return baseMapper.selectList(queryWrapper);
    }

    protected String limitOneExpression() {
        return "limit 1";
    }

}