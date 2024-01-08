package com.blog.common.mybaits;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.blog.common.util.SnowflakeUtil;

/**
 * @author zouzhangpeng
 * @desc 自定义Id生成策略
 */
public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Long nextId(Object entity) {
        return (Long)SnowflakeUtil.getId();
    }
}