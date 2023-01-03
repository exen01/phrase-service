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

