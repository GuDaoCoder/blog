package com.blog.common.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.common.base.entity.BaseEntity;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface IBaseCrudService<Entity extends BaseEntity> extends IService<Entity> {

    Entity getOneOrThrow(Long id);

}
