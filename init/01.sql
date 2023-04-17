create database if not exists users;

use users;
create table if not exists users
(
    id            int auto_increment primary key,
    username      varchar(255) not null,
    amount_of_posts int
);

