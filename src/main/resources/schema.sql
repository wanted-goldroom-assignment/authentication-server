-- users
CREATE TABLE IF NOT EXISTS users
(
    id         bigint auto_increment primary key comment 'ID',
    user_token varchar(255) not null comment 'user_token',
    login_id   varchar(10)  not null comment '로그인 아이디',
    password   varchar(256) not null comment '비밀번호'
) comment 'users' charset = utf8;

-- token
create table tokens
(
    user_token varchar(255) not null comment 'user_token',
    token      varchar(255) not null comment 'refresh_token'
) comment 'tokens' charset = utf8;
