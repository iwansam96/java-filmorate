insert into FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA)
VALUES ( 'film1',
         'description for film1',
         '2020-11-23',
         173,
         (select MPA_ID from MPAS where MPAS.MPA_NAME='NC_17')
       );
insert into FILMS_GENRES (FILM_ID, GENRE_ID)
values ( (select FILM_ID from FILMS where FILM_NAME='film1'),
         (select GENRE_ID from GENRES where GENRES.GENRE_NAME='CARTOON') );

insert into FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA)
values ( 'film2',
         'description for film2',
         '2007-09-23',
         260,
         (select MPA_ID from MPAS where MPAS.MPA_NAME='PG_13')
       );
insert into FILMS_GENRES (FILM_ID, GENRE_ID)
values ( (select FILM_ID from FILMS where FILM_NAME='film2'),
         (select GENRE_ID from GENRES where GENRES.GENRE_NAME='DOCUMENTARY') );
insert into FILMS_GENRES (FILM_ID, GENRE_ID)
values ( (select FILM_ID from FILMS where FILM_NAME='film2'),
         (select GENRE_ID from GENRES where GENRES.GENRE_NAME='CARTOON') );


insert into USERS (EMAIL, LOGIN, USERNAME, BIRTHDAY)
values ( 'user1@mailcat.net', 'user1', 'username1', '1987-06-03' );

insert into LIKES (FILM_ID, USER_ID)
values (
           (select FILMS.FILM_ID from FILMS where FILM_NAME='film1'),
           (select USERS.USER_ID from USERS where LOGIN='user1')
       );


insert into USERS (EMAIL, LOGIN, USERNAME, BIRTHDAY)
values ( 'user2@mailcat.net', 'user2', 'username2', '1978-10-12' );
insert into USERS (EMAIL, LOGIN, USERNAME, BIRTHDAY)
values ( 'user3@mailcat.net', 'user3', 'username3', '1923-09-27' );

insert into FRIENDS (USER_ID, FRIEND_ID, IS_CONFIRMED)
values (
           (select USERS.USER_ID from USERS where LOGIN='user2'),
           (select USERS.USER_ID from USERS where LOGIN='user3'),
           false
       );

insert into USERS (EMAIL, LOGIN, USERNAME, BIRTHDAY)
values ( 'user4@mailcat.net', 'user4', 'username4', '1978-10-12' );
insert into USERS (EMAIL, LOGIN, USERNAME, BIRTHDAY)
values ( 'user5@mailcat.net', 'user5', 'username5', '1923-09-27' );

insert into FRIENDS (USER_ID, FRIEND_ID, IS_CONFIRMED)
values (
           (select USERS.USER_ID from USERS where LOGIN='user4'),
           (select USERS.USER_ID from USERS where LOGIN='user5'),
           true
       );