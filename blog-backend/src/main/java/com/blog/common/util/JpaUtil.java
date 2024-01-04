package com.blog.common.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blog.common.base.response.PageResponse;
import com.blog.common.jpa.wrapper.QueryWrapper;
import com.querydsl.core.types.dsl.EntityPathBase;

/**
 * @author zouzhangpeng
 * @desc jpa工具类
 */
@Component
public class JpaUtil {

    private static EntityManager entityManager;

    private static CriteriaBuilderFactory criteriaBuilderFactory;

    @Autowired
    public JpaUtil(EntityManager entityManager, CriteriaBuilderFactory criteriaBuilderFactory) {
        JpaUtil.entityManager = entityManager;
        JpaUtil.criteriaBuilderFactory = criteriaBuilderFactory;
    }

    /**
     * 将数据库分页信息转为返回给前端的
     * 
     * @param page Page<Entity>
     * @param converter Function<Entity
     * @return PageResponse<Response>
     */
    public static <Response, Entity> PageResponse<Response> toPageResult(Page<Entity> page,
        Function<Entity, Response> converter) {
        PageResponse<Response> pageResponse = new PageResponse<>();
        pageResponse.setPageNumber(page.getNumber() + 1).setPageSize(page.getSize()).setTotal(page.getTotalElements());
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            pageResponse.setItems(page.getContent().stream().map(converter).collect(Collectors.toList()));
        }
        return pageResponse;
    }

    /**
     * like '%?%'
     * 
     * @param value Object
     * @return java.lang.String
     */
    public static String likeExpression(Object value) {
        return "%" + value + "%";
    }

    /**
     * like '%?'
     * 
     * @param value Object
     * @return java.lang.String
     */
    public static String likeLeftExpression(Object value) {
        return "%" + value;
    }

    /**
     * like '%?'
     * 
     * @param value Object
     * @return java.lang.String
     */
    public static String likeRightExpression(Object value) {
        return value + "%";
    }

    /**
     * 创建查询构造器
     * 
     * @param entityPathBase EntityPathBase<Entity>
     * @return QueryWrapper<Entity>
     */
    public static <Entity> QueryWrapper<Entity> query(EntityPathBase<Entity> entityPathBase) {
        QueryWrapper<Entity> queryWrapper = new QueryWrapper<>(entityPathBase);
        return queryWrapper;
    }

    /**
     * 构建BlazeJPAQuery
     * 
     * @param entityPathBase EntityPathBase<Entity>
     * @return com.blazebit.persistence.querydsl.BlazeJPAQuery<Entity>
     */
    public static <Entity> BlazeJPAQuery<Entity> select(EntityPathBase<Entity> entityPathBase) {
        return new BlazeJPAQuery<>(entityManager, criteriaBuilderFactory).select(entityPathBase).from(entityPathBase);
    }

    /**
     * 分页查询
     * 
     * @param query BlazeJPAQuery<Entity>
     * @param pageable Pageable
     * @return org.springframework.data.domain.Page<Entity>
     */
    public static <Entity> Page<Entity> page(BlazeJPAQuery<Entity> query, Pageable pageable) {
        long count = query.fetchCount();
        List<Entity> entities = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(entities, pageable, count);
    }
}
