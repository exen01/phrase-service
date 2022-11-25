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