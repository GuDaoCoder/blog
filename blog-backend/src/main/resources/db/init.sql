-- auto-generated definition
create table t_category
(
    category_id        bigint auto_increment comment '分类Id'
        primary key,
    category_name      varchar(256) not null comment '分类名称',
    parent_category_id bigint       not null comment '上级分类Id',
    full_id            varchar(512) not null comment '全路径Id',
    order_no           int          not null comment '顺序',
    level              int          not null comment '级别',
    create_by          bigint       not null comment '创建用户Id',
    create_time        datetime     not null comment '创建时间',
    update_by          bigint       not null comment '更新用户Id',
    update_time        datetime     not null comment '更新时间'
)
    comment '文章分类表';

-- auto-generated definition
create table t_post
(
    post_id               bigint auto_increment comment '文章Id'
        primary key,
    title                 varchar(256) not null comment '标题',
    summary               text         null comment '摘要',
    cover_picture_url     varchar(512) not null comment '封面图片链接',
    status                varchar(64)  not null comment '文章状态',
    source                varchar(64)  not null comment '文章来源',
    category_id           bigint       not null comment '所属分类Id',
    top                   tinyint(1)   not null comment '是否置顶',
    enable_comment        tinyint(1)   not null comment '是否开启评论',
    publish_time          datetime     null comment '发布时间',
    remove_time           datetime     null comment '下架时间',
    file_last_update_time datetime     not null comment '文章对应文件最后更新时间',
    create_by             bigint       not null comment '创建用户Id',
    create_time           datetime     not null comment '创建时间',
    update_by             bigint       not null comment '更新用户Id',
    update_time           datetime     not null comment '更新时间'
)
    comment '文章信息表';

-- auto-generated definition
create table t_post_content
(
    post_content_id int auto_increment comment '文章内容Id'
        primary key,
    post_id         bigint   not null comment '文章Id',
    content         text     null comment '文章内容',
    create_by       bigint   not null comment '创建用户Id',
    create_time     datetime not null comment '创建时间',
    update_by       bigint   not null comment '更新用户Id',
    update_time     datetime not null comment '更新时间'
)
    comment '文章内容表';

-- auto-generated definition
create table t_post_tag_rela
(
    post_tag_rela_id bigint auto_increment comment '文章标签关系主键'
        primary key,
    post_id          bigint   not null comment '文章Id',
    tag_id           bigint   not null comment '标签Id',
    create_by        bigint   not null comment '创建用户Id',
    create_time      datetime not null comment '创建时间',
    update_by        bigint   not null comment '更新用户Id',
    update_time      datetime not null comment '更新时间'
)
    comment '文章标签关系表';

-- auto-generated definition
create table t_tag
(
    tag_id      bigint auto_increment comment '标签Id'
        primary key,
    tag_name    varchar(128) not null comment '标签名称',
    color       varchar(20)  not null comment '颜色',
    create_by   bigint       not null comment '创建用户Id',
    create_time datetime     not null comment '创建时间',
    update_by   bigint       not null comment '更新用户Id',
    update_time datetime     not null comment '更新时间'
)
    comment '标签表';

-- auto-generated definition
create table t_user
(
    user_id         bigint auto_increment comment '用户Id'
        primary key,
    username        varchar(256) not null comment '用户名',
    password        varchar(512) not null comment '密码',
    nick_name       varchar(128) not null comment '用户昵称',
    email           varchar(128) null comment '邮箱',
    phone           varchar(20)  null comment '手机号码',
    sex             varchar(20)  null comment '性别',
    avatar          varchar(256) null comment '头像',
    last_login_ip   varchar(32)  null comment '最后登录Ip',
    last_login_time datetime     null comment '最后登陆时间',
    create_by       bigint       not null comment '创建用户Id',
    create_time     datetime     not null comment '创建时间',
    update_by       bigint       not null comment '更新用户Id',
    update_time     datetime     not null comment '更新时间'
)
    comment '用户信息表';

-- auto-generated definition
create table t_blog_git_repository
(
    repository_id bigint auto_increment comment '主键'
        primary key,
    url           varchar(256) not null comment '仓库地址',
    local_path    varchar(256) not null comment '本地下载路径',
    username      varchar(256) null comment '用户名',
    password      varchar(512) null comment '密码',
    branch        varchar(64)  null comment '分支',
    create_by     bigint       not null comment '创建人Id',
    create_time   datetime     not null comment '创建时间',
    update_by     bigint       not null comment '更新人Id',
    update_time   datetime     not null comment '更新时间'
)
    comment '博客git仓库地址';

