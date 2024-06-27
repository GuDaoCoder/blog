package com.blog.biz.support;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class GitHelper {

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

	private Git git;

	public GitHelper(String gitRepositoryUrl, String localRepositoryPath, String username, String password) {
		this.gitRepositoryUrl = gitRepositoryUrl;
		this.localRepositoryPath = localRepositoryPath;
		this.username = username;
		this.password = password;
		this.git = init();
	}

	private Git init() {
		try {
			return Git.open(new File(localRepositoryPath + File.separator + ".git"));
		}
		catch (IOException e) {
			return cloneProject();
		}
	}

	/**
	 * 切换分支
	 * @param branch
	 * @throws Exception
	 */
	@SneakyThrows
	public void checkout(String branch) {
		// 如果分支在本地已存在，直接checkout
		if (existsBranch(branch)) {
			this.git.checkout().setCreateBranch(false).setName(branch).call();
		}
		else {
			// 如果分支在本地不存在，需要从远程分支上面checkout。
			this.git.checkout().setCreateBranch(true).setName(branch).setStartPoint("origin/" + branch).call();
		}
	}

	/**
	 * clone项目到指定目录
	 * @return Git
	 **/
	@SneakyThrows
	private Git cloneProject() {
		CloneCommand cloneCommand = Git.cloneRepository()
			.setURI(this.gitRepositoryUrl)
			.setDirectory(new File(this.localRepositoryPath));
		if (StringUtils.isNoneBlank(this.username, this.password)) {
			cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.username, this.password));
		}
		return cloneCommand.call();
	}

	/**
	 * 判断分支是否存在
	 * @param branch
	 * @return
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean existsBranch(String branch) {
		List<Ref> refs = this.git.branchList().call();
		for (Ref ref : refs) {
			if (ref.getName().contains(branch)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * pull
	 * @return void
	 **/
	@SneakyThrows
	public void pull() {
		this.git.pull().call();
	}

	/**
	 * 关闭git
	 */
	public void close() {
		if (this.git != null) {
			this.git.close();
		}
	}

}
