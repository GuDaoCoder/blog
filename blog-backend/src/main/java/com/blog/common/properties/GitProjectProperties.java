package com.blog.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "git.project", ignoreInvalidFields = true)
public class GitProjectProperties {

	/**
	 * git仓库地址
	 */
	private String gitRepositoryUrl;

	/**
	 * 本地仓库地址
	 */
	private String localRepositoryPath;

	/**
	 * git用户名
	 */
	private String username;

	/**
	 * git用户密码
	 */
	private String password;

	/**
	 * 分支
	 */
	private String branch;

}
