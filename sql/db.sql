create database blog;
use blog;

create table artical
(
    post_id       int auto_increment comment '文章id'
        primary key,
    title         varchar(128)                       null comment '文章标题',
    context       text                               null comment '文章内容',
    user_id       int                                not null comment '作者id',
    created       datetime default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified datetime default CURRENT_TIMESTAMP null comment '修改时间'
) engine=innodb default charset=utf8mb4 collate utf8mb4_general_ci;


create table user
(
    user_id       int auto_increment comment '用户id'
        primary key,
    username      varchar(32)                        not null comment '账号',
    password      varchar(32)                        not null comment '密码',
    email         varchar(128)                       null comment '邮箱',
    created       datetime default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified datetime default CURRENT_TIMESTAMP null comment '修改时间'
)engine=innodb default charset=utf8mb4 collate utf8mb4_general_ci;
