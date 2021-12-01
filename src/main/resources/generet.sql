create table databasechangeloglock
(
    id          integer not null
        primary key,
    locked      boolean not null,
    lockgranted timestamp,
    lockedby    varchar(255)
);

alter table databasechangeloglock
    owner to admin;

create table databasechangelog
(
    id            varchar(255) not null,
    author        varchar(255) not null,
    filename      varchar(255) not null,
    dateexecuted  timestamp    not null,
    orderexecuted integer      not null,
    exectype      varchar(10)  not null,
    md5sum        varchar(35),
    description   varchar(255),
    comments      varchar(255),
    tag           varchar(255),
    liquibase     varchar(20),
    contexts      varchar(255),
    labels        varchar(255),
    deployment_id varchar(10)
);

alter table databasechangelog
    owner to admin;

create table role
(
    role_id integer generated by default as identity
        primary key,
    role    varchar(255)
);

alter table role
    owner to admin;

create table user_entity
(
    id         integer generated by default as identity
        primary key,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    role       integer
        constraint fk_role_id
            references role
);

alter table user_entity
    owner to admin;

insert into role values (0,'USER');
insert into role values (1,'ADMIN');
insert into user_entity values (100,'1111','1111', '$2a$12$oYf5JSxoQzRs2WgP3/1o8uRaPIYqbVACCyNnWj2PhN0b/2aFd6kuO', 0);
insert into user_entity values (101,'2222','2222', '$2a$12$oYf5JSxoQzRs2WgP3/1o8uRaPIYqbVACCyNnWj2PhN0b/2aFd6kuO', 1);
insert into user_entity values (102,'3333','3333', '$2a$12$oYf5JSxoQzRs2WgP3/1o8uRaPIYqbVACCyNnWj2PhN0b/2aFd6kuO', 0);
