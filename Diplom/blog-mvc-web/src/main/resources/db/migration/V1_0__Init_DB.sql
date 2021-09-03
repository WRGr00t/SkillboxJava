drop table if exists captcha_codes;
drop table if exists global_settings;
drop table if exists hibernate_sequence;
drop table if exists post_comments;
drop table if exists post_votes;
drop table if exists posts;
drop table if exists tag2post;
drop table if exists tags;
drop table if exists users;

create table captcha_codes (
    id integer not null,
    code TINYTEXT not null,
    secret_code TINYTEXT not null,
    time datetime(6) not null,
     primary key (id)) engine=InnoDB;

create table global_settings (
    id integer not null,
    code varchar(255) not null,
    name varchar(255) not null,
    value varchar(255) not null,
    primary key (id)) engine=InnoDB;

create table hibernate_sequence (next_val bigint) engine=InnoDB;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

create table post_comments (
    id integer not null,
    parent_id integer,
    text TEXT not null,
    time datetime(6) not null,
    post_id integer not null,
    user_id integer not null,
     primary key (id)) engine=InnoDB;

create table post_votes (
    id integer not null,
    time datetime(6) not null,
    value TINYINT not null,
    post_id integer not null,
    user_id integer not null,
    primary key (id)) engine=InnoDB;

create table posts (
    id integer not null,
    is_active TINYINT(1) not null,
    moderation_status varchar(255) default 'NEW' not null,
    moderator_id integer,
    text TEXT not null,
    time datetime(6) not null,
    title varchar(255) not null,
    view_count integer not null,
    user_id integer not null,
    primary key (id)) engine=InnoDB;

create table tag2post (
    id integer not null,
    post_id integer not null,
    tag_id integer not null,
    primary key (id)) engine=InnoDB;

create table tags (
    id integer not null,
    name varchar(255) not null,
    primary key (id)) engine=InnoDB;

create table users (
    id integer not null,
    email varchar(255) not null,
    is_moderator TINYINT(1) not null,
    password varchar(255) not null,
    photo TEXT,
    reg_time datetime(6) not null,
    name varchar(255) not null,
    code TINYTEXT,
    primary key (id)) engine=InnoDB;

alter table captcha_codes add constraint UK_83mmu42md6s7q4farjo2c6vrg unique (code);
alter table post_comments add constraint post_comments_fk foreign key (post_id) references posts (id) on delete cascade;
alter table post_comments add constraint post_comments_user_fk foreign key (user_id) references users (id) on delete cascade;
alter table post_votes add constraint post_votes_fk foreign key (post_id) references posts (id) on delete cascade;
alter table post_votes add constraint post_votes_user_fk foreign key (user_id) references users (id) on delete cascade;
alter table posts add constraint post_users_fk foreign key (user_id) references users (id) on delete cascade;
alter table tag2post add constraint t2p_posts_fk foreign key (post_id) references posts (id) on delete cascade;
alter table tag2post add constraint t2p_tags_fk foreign key (tag_id) references tags (id) on delete cascade;
alter table users add constraint users_captcha_fk foreign key (code) references captcha_codes (code);
