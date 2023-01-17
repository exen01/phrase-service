CREATE TABLE phrase_public.user
(
    id bigint auto_increment,
    nickname varchar(15) not null,
    password varchar(100) not null,
    access_token varchar(100) not null,
    time_insert timestamp not null default current_timestamp,
    primary key(`id`),
    unique key `nickname_password`(`nickname`, `password`)
) collate utf8_bin;

alter table user drop index `nickname_password`;
alter table user add unique (nickname);
alter table user add unique (access_token);

CREATE TABLE phrase_public.phrase
(
    id bigint auto_increment,
    user_id bigint not null,
    text varchar(140) not null,
    time_insert timestamp not null default current_timestamp,
    primary key(`id`),
    foreign key(`user_id`) references user(`id`)
) collate utf8_bin;

CREATE TABLE phrase_public.tag
(
    id bigint auto_increment,
    text varchar(25) not null,
    time_insert timestamp not null default current_timestamp,
    primary key(`id`),
    unique(`text`)
) collate utf8_bin;
INSERT INTO tag(text) VALUE ('test');

CREATE TABLE phrase_public.phrase_tag
(
    id bigint auto_increment,
    phrase_id bigint not null,
    tag_id bigint not null,
    time_insert timestamp not null default current_timestamp,
    primary key(`id`),
    foreign key(`phrase_id`) references phrase(`id`),
    foreign key(`tag_id`) references tag(`id`),
    unique `phrase_id_tag_id` (`phrase_id`, `tag_id`)
) collate utf8_bin;

CREATE TABLE phrase_public.subscription(
    id BIGINT AUTO_INCREMENT,
    sub_user_id BIGINT NOT NULL,
    pub_user_id BIGINT NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`sub_user_id`) REFERENCES user(`id`),
    FOREIGN KEY(`pub_user_id`) REFERENCES user(`id`),
    UNIQUE `sub_user_id_pub_user_id`(`sub_user_id`, `pub_user_id`)
) COLLATE utf8_bin;

CREATE TABLE test_scheduler_lock(
    instance_name VARCHAR(64) NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE shedlock(
    NAME VARCHAR(64) NOT NULL,
    lock_until TIMESTAMP(3) NOT NULL,
    locked_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    locked_by VARCHAR(255) NOT NULL,
    PRIMARY KEY(`name`)
);

CREATE TABLE phrase_public.like_phrase(
    id BIGINT AUTO_INCREMENT,
    phrase_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`phrase_id`) REFERENCES phrase(`id`),
    FOREIGN KEY(`user_id`) REFERENCES user(`id`),
    UNIQUE `phrase_id_user_id`(`phrase_id`, `user_id`)
) COLLATE utf8_bin;

CREATE TABLE phrase_public.comment(
    id BIGINT AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    phrase_id BIGINT NOT NULL,
    text VARCHAR(140) NOT NULL,
    time_insert TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`phrase_id`) REFERENCES phrase(`id`),
    FOREIGN KEY(`user_id`) REFERENCES user(`id`)
) COLLATE utf8_bin;