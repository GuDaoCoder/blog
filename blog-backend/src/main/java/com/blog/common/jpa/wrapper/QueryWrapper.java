package com.blog.common.jpa.wrapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blog.common.util.JpaUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;

/**
 * @author zouzhangpeng
 * @desc Jpa查询构造器
 */
public class QueryWrapper<Entity> {

    private BlazeJPAQuery<Entity> query;

    public QueryWrapper(EntityPathBase<Entity> entityPathBase) {
        this.query = JpaUtil.select(entityPathBase);
    }

    /**
     * = ?
     *
     * @param expression SimpleExpression<S>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value> QueryWrapper<Entity> eq(SimpleExpression<Value> expression, Value value) {
        return eq(true, expression, value);
    }

    /**
     * = ?
     * 
     * @param condition boolean
     * @param expression SimpleExpression<S>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value> QueryWrapper<Entity> eq(boolean condition, SimpleExpression<Value> expression, Value value) {
        if (condition) {
            this.query.where(expression.eq(value));
        }
        return this;
    }

    /**
     * like '%?%'
     *
     * @param expression StringExpression
     * @param value String
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */

    public QueryWrapper<Entity> like(StringExpression expression, String value) {
        return like(true, expression, value);
    }

    /**
     * like '%?%'
     * 
     * @param condition boolean
     * @param expression StringExpression
     * @param value String
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */

    public QueryWrapper<Entity> like(boolean condition, StringExpression expression, String value) {
        if (condition) {
            this.query.where(expression.like(JpaUtil.likeExpression(value)));
        }
        return this;
    }

    /**
     * like '%?%'
     *
     * @param expression NumberExpression<S>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> like(NumberExpression<Value> expression,
        Value value) {
        return like(true, expression, value);
    }

    /**
     * like '%?%'
     * 
     * @param condition boolean
     * @param expression NumberExpression<S>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> like(boolean condition,
        NumberExpression<Value> expression, Value value) {
        if (condition) {
            this.query.where(expression.like(JpaUtil.likeExpression(value)));
        }
        return this;
    }

    /**
     * between ? and ?
     *
     * @param expression LocalDateTime>
     * @param from LocalDateTime
     * @param to LocalDateTime
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public QueryWrapper<Entity> between(DateTimePath<LocalDateTime> expression, LocalDateTime from, LocalDateTime to) {
        return between(true, expression, from, to);
    }

    /**
     * between ? and ?
     * 
     * @param condition boolean
     * @param expression LocalDateTime>
     * @param from LocalDateTime
     * @param to LocalDateTime
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public QueryWrapper<Entity> between(boolean condition, DateTimePath<LocalDateTime> expression, LocalDateTime from,
        LocalDateTime to) {
        if (condition) {
            this.query.where(expression.between(from, to));
        }
        return this;
    }

    /**
     * < ?
     *
     * @param expression NumberExpression<Value>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> lt(NumberExpression<Value> expression,
        Value value) {
        return lt(true, expression, value);
    }

    /**
     * < ?
     * 
     * @param condition boolean
     * @param expression NumberExpression<Value>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> lt(boolean condition,
        NumberExpression<Value> expression, Value value) {
        if (condition) {
            this.query.where(expression.lt(value));
        }
        return this;
    }

    /**
     * <= ?
     *
     * @param expression NumberExpression<Value>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> le(NumberExpression<Value> expression,
        Value value) {
        return le(true, expression, value);
    }

    /**
     * <= ?
     * 
     * @param condition boolean
     * @param expression NumberExpression<Value>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> le(boolean condition,
        NumberExpression<Value> expression, Value value) {
        if (condition) {
            this.query.where(expression.loe(value));
        }
        return this;
    }

    /**
     * > ?
     *
     * @param expression NumberExpression<Value>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> gt(NumberExpression<Value> expression,
        Value value) {
        return gt(true, expression, value);
    }

    /**
     * > ?
     *
     * @param condition boolean
     * @param expression NumberExpression<Value>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> gt(boolean condition,
        NumberExpression<Value> expression, Value value) {
        if (condition) {
            this.query.where(expression.gt(value));
        }
        return this;
    }

    /**
     * >= ?
     *
     * @param expression NumberExpression<Value>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> ge(NumberExpression<Value> expression,
        Value value) {
        return ge(true, expression, value);
    }

    /**
     * >= ?
     * 
     * @param condition boolean
     * @param expression NumberExpression<Value>
     * @param value Value
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Number & Comparable<?>> QueryWrapper<Entity> ge(boolean condition,
        NumberExpression<Value> expression, Value value) {
        if (condition) {
            this.query.where(expression.goe(value));
        }
        return this;
    }

    /**
     * order by
     * 
     * @param o OrderSpecifier<Value>
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public <Value extends Comparable<?>> QueryWrapper<Entity> order(OrderSpecifier<Value> o) {
        this.query.orderBy(o);
        return this;
    }

    /**
     * limit ?
     * 
     * @param limit long
     * @return com.blog.common.jpa.wrapper.QueryWrapper<Entity>
     */
    public QueryWrapper<Entity> limit(long limit) {
        this.query.limit(limit);
        return this;
    }

    /**
     * 查询数量
     * 
     * @param
     * @return long
     */
    public long count() {
        return this.query.fetchCount();
    }

    /**
     * 查询单条数据
     * 
     * @param
     * @return Entity
     */
    public Optional<Entity> fetchOne() {
        return Optional.ofNullable(this.query.fetchOne());
    }

    /**
     * 查询多条数据
     *
     * @param
     * @return java.util.List<Entity>
     */
    public List<Entity> fetchList() {
        return this.query.fetch();
    }

    /**
     * 分页查询
     *
     * @param pageable Pageable
     * @return org.springframework.data.domain.Page<Entity>
     */
    public Page<Entity> page(Pageable pageable) {
        return JpaUtil.page(this.query, pageable);
    }
}
