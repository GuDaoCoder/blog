package com.blog.common.support;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.BatchingProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Slf4j
@Setter
@Getter
public class GitOperation {

    /**
     * git仓库地址
     */
    private String gitRepositoryUrl;

    /**
     * 本地仓库地址
     */
    private String localRepositoryPath;

    /**
     * 分支
     */
    private String branch;

    /**
     * git用户名
     */
    private String username;

    /**
     * git用户密码
     */
    private String password;

    /**
     * 操作日志发送器
     */
    private List<GitOperationLogSender> operationLogSenders;

    /**
     * clone或更新代码仓库
     * @param
     * @return void
     **/
    public void cloneOrUpdateRepository() throws IOException, GitAPIException {
        File localRepoDir = new File(localRepositoryPath);

        UsernamePasswordCredentialsProvider credentialsProvider = StringUtils.isNoneBlank(username, password)
                ? new UsernamePasswordCredentialsProvider(username, password) : null;
        CustomProgressMonitor progressMonitor = CollectionUtils.isNotEmpty(operationLogSenders)
                ? new CustomProgressMonitor(operationLogSenders) : null;

        if (localRepoDir.exists() && new File(localRepoDir, ".git").exists()) {
            // 目标路径已经存在Git仓库，进行同步
            try (Git git = Git.open(localRepoDir)) {
                Repository repository = git.getRepository();
                // 当前分支
                String currentBranch = repository.getBranch();
                if (!currentBranch.equals(branch)) {
                    // 切换到指定分支
                    git.checkout().setName(branch).call();
                }
                // 拉取最新代码
                git.pull()
                    .setRemoteBranchName(branch)
                    .setCredentialsProvider(credentialsProvider)
                    .setProgressMonitor(progressMonitor)
                    .call();
            }
        }
        else {
            // 目标路径不存在，进行克隆
            Git.cloneRepository()
                .setURI(gitRepositoryUrl)
                .setDirectory(localRepoDir)
                .setBranchesToClone(List.of("refs/heads/" + branch))
                .setBranch("refs/heads/" + branch)
                .setCredentialsProvider(credentialsProvider)
                .setProgressMonitor(progressMonitor)
                .call();
        }
    }

    public static class Builder {

        private final GitOperation gitOperation;

        public Builder() {
            this.gitOperation = new GitOperation();
        }

        public Builder gitRepositoryUrl(String gitRepositoryUrl) {
            this.gitOperation.gitRepositoryUrl = gitRepositoryUrl;
            return this;
        }

        public Builder localRepositoryPath(String localRepositoryPath) {
            this.gitOperation.localRepositoryPath = localRepositoryPath;
            return this;
        }

        public Builder branch(String branch) {
            this.gitOperation.branch = branch;
            return this;
        }

        public Builder username(String username) {
            this.gitOperation.username = username;
            return this;
        }

        public Builder password(String password) {
            this.gitOperation.password = password;
            return this;
        }

        public Builder operationLogSenders(List<GitOperationLogSender> operationLogSenders) {
            this.gitOperation.operationLogSenders = operationLogSenders;
            return this;
        }

        public GitOperation build() {
            return this.gitOperation;
        }

    }

    public static class CustomProgressMonitor extends BatchingProgressMonitor {

        private final List<GitOperationLogSender> operationLogSenders;

        public CustomProgressMonitor(List<GitOperationLogSender> operationLogSenders) {
            this.operationLogSenders = operationLogSenders;
        }

        @Override
        protected void onUpdate(String taskName, int workCurr, Duration duration) {
            StringBuilder s = new StringBuilder();
            format(s, taskName, workCurr, duration);
            send(s);
        }

        @Override
        protected void onEndTask(String taskName, int workCurr, Duration duration) {
            StringBuilder s = new StringBuilder();
            format(s, taskName, workCurr, duration);
            s.append("\n"); //$NON-NLS-1$
            send(s);
        }

        @Override
        protected void onUpdate(String taskName, int cmp, int totalWork, int pcnt, Duration duration) {
            StringBuilder s = new StringBuilder();
            format(s, taskName, cmp, totalWork, pcnt, duration);
            send(s);
        }

        @Override
        protected void onEndTask(String taskName, int cmp, int totalWork, int pcnt, Duration duration) {
            StringBuilder s = new StringBuilder();
            format(s, taskName, cmp, totalWork, pcnt, duration);
            s.append("\n"); //$NON-NLS-1$
            send(s);
        }

        private void format(StringBuilder s, String taskName, int workCurr, Duration duration) {
            s.append("\r"); //$NON-NLS-1$
            s.append(taskName);
            s.append(": "); //$NON-NLS-1$
            while (s.length() < 25)
                s.append(' ');
            s.append(workCurr);
            appendDuration(s, duration);
        }

        private void format(StringBuilder s, String taskName, int cmp, int totalWork, int pcnt, Duration duration) {
            s.append("\r"); //$NON-NLS-1$
            s.append(taskName);
            s.append(": "); //$NON-NLS-1$
            while (s.length() < 25)
                s.append(' ');

            String endStr = String.valueOf(totalWork);
            String curStr = String.valueOf(cmp);
            while (curStr.length() < endStr.length())
                curStr = " " + curStr; //$NON-NLS-1$
            if (pcnt < 100)
                s.append(' ');
            if (pcnt < 10)
                s.append(' ');
            s.append(pcnt);
            s.append("% ("); //$NON-NLS-1$
            s.append(curStr);
            s.append('/');
            s.append(endStr);
            s.append(')');
            appendDuration(s, duration);
        }

        private void send(StringBuilder s) {
            if (CollectionUtils.isNotEmpty(operationLogSenders)) {
                operationLogSenders.forEach(sender -> {
                    try {
                        sender.send(s.toString());
                    }
                    catch (Exception e) {
                        log.error("{} send git operation log error", sender.getClass().getName(), e);
                    }
                });
            }
        }

    }

    public interface GitOperationLogSender {

        /**
         * 发送操作日志信息
         * @param message
         * @return void
         **/
        void send(String message) throws Exception;

    }

}
