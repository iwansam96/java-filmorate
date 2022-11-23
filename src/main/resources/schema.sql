-- drop table if exists FILMS;
-- drop table if exists USERS;
-- drop table if exists MPAS;
-- drop table if exists GENRES;
-- drop table if exists LIKES;
-- drop table if exists FRIENDS;
-- drop table if exists FILMS_GENRES;

create table if not exists GENRES (
    GENRE_ID integer generated by default as identity primary key,
    GENRE_NAME varchar not null
);
create table if not exists MPAS (
    MPA_ID integer generated by default as identity primary key,
    MPA_NAME varchar not null
);
create table if not exists FILMS (
    FILM_ID integer generated by default as identity primary key,
    FILM_NAME varchar not null,
    DESCRIPTION varchar,
    RELEASE_DATE date,
    DURATION integer,
    MPA integer references MPAS (MPA_ID) on delete cascade on update cascade
);
create table if not exists FILMS_GENRES (
    FILM_ID integer references FILMS (FILM_ID) on delete cascade on update cascade,
    GENRE_ID integer references GENRES (GENRE_ID) on delete cascade on update cascade
);
create table if not exists USERS (
    USER_ID integer generated by default as identity primary key,
    EMAIL varchar not null,
    LOGIN varchar not null,
    USERNAME varchar,
    BIRTHDAY date
);
create table if not exists LIKES
(
    FILM_ID integer references FILMS (FILM_ID) on delete cascade on update cascade,
    USER_ID integer references USERS (USER_ID) on delete cascade on update cascade
);
create table if not exists FRIENDS (
    USER_ID integer references USERS (USER_ID) on delete cascade on update cascade,
    FRIEND_ID integer references USERS (USER_ID) on delete cascade on update cascade,
    IS_CONFIRMED boolean default false
);
