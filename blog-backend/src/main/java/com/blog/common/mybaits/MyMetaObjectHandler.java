package com.blog.common.mybaits;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.blog.common.context.UserContext;
import com.blog.common.domain.UserDetail;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author zouzhangpeng
 * @desc 自定义数据填充策略
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "createBy", Long.class,
				Optional.ofNullable(UserContext.get()).map(UserDetail::getUserId).orElse(-1L));
		this.strictInsertFill(metaObject, "updateBy", Long.class,
				Optional.ofNullable(UserContext.get()).map(UserDetail::getUserId).orElse(-1L));
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "updateBy", Long.class,
				Optional.ofNullable(UserContext.get()).map(UserDetail::getUserId).orElse(-1L));
	}

}