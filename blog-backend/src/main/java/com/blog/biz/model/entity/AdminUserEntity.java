package com.blog.biz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.biz.enums.Gender;
import com.blog.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author zouzhangpeng
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_admin_user")
public class AdminUserEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = -3648600719223231438L;

	/**
	 * 用户Id
	 */
	@TableId(type = IdType.AUTO)
	private Long userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户邮箱
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 用户性别
	 */
	private Gender sex;

	/**
	 * 头像地址
	 */
	private String avatar;

	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;

	/**
	 * 最后登录时间
	 */
	private LocalDateTime lastLoginTime;

}
