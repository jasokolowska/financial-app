--liquibase formatted sql
--changeset jasokolowska:7
create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

--changeset jasokolowska:8
create unique index ix_auth_username on authorities (username,authority);

--changeset jasokolowska:9
insert into users (user_id, username, password, first_name, last_name)
values (1, 'admin', '{bcrypt}$2a$10$upzXFsFUOClFRR69OMKF8eajGMRs0vhcSHqvNDKy9yfW45w7o9z6O', 'Jan', 'Kowalski');
insert into authorities (username, authority) values ('admin','ROLE_ADMIN');