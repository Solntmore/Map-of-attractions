drop table if exists attractions cascade;
drop table if exists cities cascade;

create table attractions (
    id int8 generated by default as identity,
    name varchar(255),
    status varchar(512) default 'AVAILABLE',
    website varchar(255),
    city_id int8,
    primary key (id)
);

create table cities (
    id int8 generated by default as identity,
    area int4,
    name varchar(255),
    population int8,
    website varchar(255),
    primary key (id)
);

alter table if exists attractions
    add constraint UK_632svj19y1t9wlar3d65xqix2 unique (name);

alter table if exists cities
    add constraint UK_l61tawv0e2a93es77jkyvi7qa unique (name);

alter table if exists attractions
    add constraint FKk1bhwyen25wnweuy89os529t
        foreign key (city_id)
            references cities