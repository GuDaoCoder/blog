package com.blog.common.base.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.common.base.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface IBaseCrudService<Entity extends BaseEntity> extends IService<Entity> {

	Entity getOneOrThrow(Long id);

	<T> Optional<Entity> getByField(SFunction<Entity, T> function, T value);

	<T> void removeByField(SFunction<Entity, T> function, T value);

	<T> List<Entity> listByFields(SFunction<Entity, T> function, List<T> values);

}
