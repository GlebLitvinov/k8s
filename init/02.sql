create database if not exists posts;

use posts;
create table if not exists posts
(
    id       int auto_increment primary key,
    author_id int        not null,
    text     varchar(1000) not null,
    posted_at timestamp
);

