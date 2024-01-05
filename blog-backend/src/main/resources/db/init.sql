create table t_user
(
    user_id         bigint       not null comment '用户ID'
        primary key,
    username        varchar(30)  not null comment '用户名',
    password        varchar(256) not null comment '密码',
    nick_name       varchar(30)  not null comment '用户昵称',
    email           varchar(50)  null comment '用户邮箱',
    phone           varchar(11)  null comment '手机号码',
    sex             varchar(20)  not null comment '用户性别（male-男，female-女，unknown未知）',
    avatar          varchar(256) not null comment '头像地址',
    last_login_ip   varchar(128) null comment '最后登录IP',
    last_login_time datetime     null comment '最后登录时间',
    create_by       bigint       not null comment '创建者',
    create_time     datetime     not null comment '创建时间',
    update_by       bigint       not null comment '更新者',
    update_time     datetime     not null comment '更新时间'
)
    comment '用户信息表';

create table t_tag
(
    tag_id      bigint      not null comment '标签ID'
        primary key,
    tag_name    varchar(20) not null comment '标签名称',
    order_no    int         not null comment '标签顺序',
    create_by   bigint      not null comment '创建者',
    create_time datetime    not null comment '创建时间',
    update_by   bigint      not null comment '更新者',
    update_time datetime    not null comment '更新时间'
)
    comment '标签表';

create table t_category
(
    category_id   bigint        not null comment '分类ID'
        primary key,
    category_name varchar(20)   not null comment '分类名称',
    parent_id     bigint        not null comment '上级Id',
    full_id       varchar(1000) null comment '全路径Id',
    order_no      int           not null comment '顺序',
    level         int           not null comment '等级',
    create_by     bigint        not null comment '创建者',
    create_time   datetime      not null comment '创建时间',
    update_by     bigint        not null comment '更新者',
    update_time   datetime      not null comment '更新时间'
)
    comment '分类表';





