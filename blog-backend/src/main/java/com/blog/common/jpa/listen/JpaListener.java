package com.blog.common.jpa.listen;

import com.blog.common.base.entity.BaseEntity;
import com.blog.common.context.UserContext;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @author zouzhangpeng
 * @desc
 */
public class JpaListener {

    /**
     * 插入数据时填充数据
     */
    @PrePersist
    public void preInsert(Object object) {
        if (object instanceof BaseEntity) {
            ((BaseEntity)object).setCreateTime(LocalDateTime.now()).setCreateBy(UserContext.get().getUserId())
                .setUpdateTime(LocalDateTime.now()).setUpdateBy(UserContext.get().getUserId());
        }
    }

    /**
     * 更新数据时填充数据
     */
    @PreUpdate
    public void preUpdate(Object object) {
        if (object instanceof BaseEntity) {
            ((BaseEntity)object).setUpdateTime(LocalDateTime.now()).setUpdateBy(UserContext.get().getUserId());
        }
    }
}
