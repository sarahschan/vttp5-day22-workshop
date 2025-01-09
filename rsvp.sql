-- drop the database if it exists
drop database if exists rsvp;

-- create the new database
create database if not exists rsvp;

-- select the database
use rsvp;

-- create the table
select "Creating RSVP table..." as msg;
create table rsvp(
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

-- add some data
select "Adding some data..." as msg;
insert into rsvp
        (email, phone, confirmation_date, comments)
    values
        ('alice.johnson@example.com', '94627481', '2024-01-10', 'Looking forward to it!'),
        ('bob.smith@example.com', '84759021', '2024-01-11', NULL),
        ('charlie.brown@example.com', '93474312', '2024-01-12', 'Will be bringing a guest.'),
        ('diana.prince@example.com', '80153823', '2024-01-13', NULL);