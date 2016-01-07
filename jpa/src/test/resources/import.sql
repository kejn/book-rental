create or replace TRIGGER BOOKS_TRG before insert on BOOKS for each row begin if (inserting and :new.id is null) then select BOOKS_SEQ.NEXTVAL into :new.id from SYS.DUAL; end if; end; ;
create or replace TRIGGER AUTHORS_TRG before insert on AUTHORS for each row begin if (inserting and :new.id is null) then select AUTHORS_SEQ.NEXTVAL into :new.id from SYS.DUAL; end if; end; ;
create or replace TRIGGER LIBRARIES_TRG before insert on LIBRARIES for each row begin if (inserting and :new.id is null) then select LIBRARIES_SEQ.NEXTVAL into :new.id from SYS.DUAL; end if; end; ;
create or replace TRIGGER USERS_TRG before insert on USERS for each row begin if (inserting and :new.id is null) then select USERS_SEQ.NEXTVAL into :new.id from SYS.DUAL; end if; end; ;

insert into BOOKS (id, title) values (null, 'Pierwsza książka');
insert into BOOKS (id, title) values (null, 'Książka do usunięcia #1');
insert into BOOKS (id, title) values (null, 'Książka do usunięcia #2');

insert into AUTHORS (id, first_name, last_name) values (null, 'Jan', 'Kowalski');
insert into AUTHORS (id, first_name, last_name) values (null, 'Jan', 'Brzechwa');
insert into AUTHORS (id, first_name, last_name) values (null, 'Piotr', 'Nowak');
insert into AUTHORS (id, first_name, last_name) values (null, 'Robert', 'Karolak');

insert into BOOK_AUTHOR (book_id, author_id) values (1, 1);
insert into BOOK_AUTHOR (book_id, author_id) values (1, 2);
insert into BOOK_AUTHOR (book_id, author_id) values (2, 3);
insert into BOOK_AUTHOR (book_id, author_id) values (3, 4);

insert into LIBRARIES (id, name) values (null, 'Biblioteka we Wrocławiu');
insert into LIBRARIES (id, name) values (null, 'Biblioteka w Poznaniu');
insert into LIBRARIES (id, name) values (null, 'Biblioteka w Warszawie');

insert into BOOK_LIBRARY (book_id, library_id, quantity) values (1, 1, 3);
insert into BOOK_LIBRARY (book_id, library_id, quantity) values (1, 2, 1);
insert into BOOK_LIBRARY (book_id, library_id, quantity) values (1, 3, 1);
insert into BOOK_LIBRARY (book_id, library_id, quantity) values (2, 2, 1);
insert into BOOK_LIBRARY (book_id, library_id, quantity) values (3, 3, 1);

insert into USERS (id, name, password) values (null, 'user', 'sup3R$ecre7P4$$word');
insert into USERS (id, name, password) values (null, 'userUpdateCheck', '12345678');

insert into USER_BOOK (user_id, book_id) values(2, 1);