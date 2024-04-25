package com.blog.biz.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.blog.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_git_repository")
public class GitRepositoryEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -596804980214255293L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long gitRepositoryId;

    /**
     * 仓库地址
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String url;

    /**
     * 本地下载路径
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String localPath;

    /**
     * 用户名
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String username;

    /**
     * 密码
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String password;

    /**
     * 分支
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String branch;
}
