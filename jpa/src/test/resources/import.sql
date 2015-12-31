create or replace TRIGGER BOOKS_TRG before insert on BOOKS for each row begin if (inserting and :new.id is null) then select BOOKS_SEQ.NEXTVAL into :new.id from SYS.DUAL; end if; end; ;
create or replace TRIGGER AUTHORS_TRG before insert on AUTHORS for each row begin if (inserting and :new.id is null) then select AUTHORS_SEQ.NEXTVAL into :new.id from SYS.DUAL; end if; end; ;

insert into BOOKS (id, title) values (null, 'Pierwsza książka');
insert into BOOKS (id, title) values (null, 'Książka do usunięcia #1');
insert into BOOKS (id, title) values (null, 'Książka do usunięcia #2');

insert into AUTHORS (id, first_name, last_name) values (null, 'Jan', 'Kowalski');
insert into AUTHORS (id, first_name, last_name) values (null, 'Jan', 'Brzechwa');
insert into AUTHORS (id, first_name, last_name) values (null, 'Piotr', 'Nowak');

insert into BOOK_AUTHOR (book_id, author_id) values (1, 1);
insert into BOOK_AUTHOR (book_id, author_id) values (1, 2);
insert into BOOK_AUTHOR (book_id, author_id) values (2, 3);
insert into BOOK_AUTHOR (book_id, author_id) values (3, 3);
