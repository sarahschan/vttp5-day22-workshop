-- drop the database if it exists
drop database if exists rsvp;

-- create the new database
create database if not exists rsvp;

-- select the database
use rsvp;

-- create the table
select "Creating RSVP table..." as msg;
create table rsvp(
    name varchar(128) not null,
    email varchar(128) not null,
    phone varchar(20) not null,
    confirmation_date date not null,
    comments text,

    constraint pk_email primary key (email)
);

-- grant fred access to the database
select "Granting privileges to fred" as msg;
grant all privileges on rsvp.* to 'fred'@'%';
flush privileges;