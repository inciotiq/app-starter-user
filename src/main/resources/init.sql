create database tenant1;
create table users
(
    id                      uuid    not null
        primary key,
    username                varchar(255),
    account_non_expired     boolean not null,
    account_non_locked      boolean not null,
    enabled                 boolean not null,
    credentials_non_expired boolean not null,
    password                varchar(255),
    email                   varchar(255),
    first_name              varchar(255),
    last_name               varchar(255),
    phone_number            varchar(255),
    web_page                varchar(255),
    role                    varchar(255)
);

INSERT INTO "users" (id, username, account_non_expired, account_non_locked, enabled, credentials_non_expired,
                          password, email, first_name, last_name, phone_number, web_page, role)
VALUES ('f9cd7961-c8a9-4cb6-b00b-ab906f3da978', 'admin', false, false, false, false,
        '$2a$10$hsW5rHZueKkagA0E2rRSY.D6jK7FS4no.7nLn6l2VT0HQdyV5.1vO', 'email@m.com', 'fn', 'ln', null, null, 'ADMIN');
