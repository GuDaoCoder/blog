package com.blog.common.domain;

import com.blog.biz.enums.Gender;
import lombok.Data;

/**
 * @author zouzhangpeng
 * @desc 用户详情
 */
@Data
public class UserDetail {

	/**
	 * 用户Id
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String username;

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

}
