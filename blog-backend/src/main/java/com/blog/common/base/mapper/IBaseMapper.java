package com.blog.common.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.base.entity.BaseEntity;

/**
 * @author zouzhangpeng
 * @desc
 */
public interface IBaseMapper<Entity extends BaseEntity> extends BaseMapper<Entity> {

}
