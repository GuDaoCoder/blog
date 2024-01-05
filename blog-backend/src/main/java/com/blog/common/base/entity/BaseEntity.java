package com.blog.common.base.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.blog.common.context.UserContext;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zouzhangpeng
 * @desc 数据库实体基类
 */
@Accessors(chain = true)
@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3102393059220666166L;

    /**
     * 创建用户Id
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新用户Id
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 新增数据时填充数据
     */
    @PrePersist
    public void preSave() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.createBy = UserContext.get().getUserId();
        this.updateBy = UserContext.get().getUserId();
        this.preSaveProcessor();
    }

    /**
     * 更新数据时填充数据
     */
    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
        this.updateBy = UserContext.get().getUserId();
        this.preUpdateProcessor();
    }

    public abstract Long primaryId();

    protected void preSaveProcessor() {

    }

    protected void preUpdateProcessor() {

    }
}
