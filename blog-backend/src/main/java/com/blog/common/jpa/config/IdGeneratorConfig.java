package com.blog.common.jpa.config;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.blog.common.util.SnowflakeUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc 主键生成策略
 */
@Slf4j
public class IdGeneratorConfig implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
        throws HibernateException {
        return SnowflakeUtil.getId();
    }
}
