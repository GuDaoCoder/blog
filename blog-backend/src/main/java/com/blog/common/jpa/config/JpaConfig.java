package com.blog.common.jpa.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;

/**
 * @author zouzhangpeng
 * @desc jpa配置
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.blog.biz.repository")
@EnableTransactionManagement
public class JpaConfig {

    @Bean
    public CriteriaBuilderFactory criteriaBuilderFactory(EntityManagerFactory entityManagerFactory) {
        CriteriaBuilderFactory criteriaBuilderFactory =
            Criteria.getDefault().createCriteriaBuilderFactory(entityManagerFactory);
        return criteriaBuilderFactory;
    }

}
