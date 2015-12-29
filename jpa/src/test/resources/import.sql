create or replace TRIGGER BOOKS_TRG before insert on BOOKS for each row begin if (inserting and :new.id is null) then select BOOKS_SEQ.NEXTVAL into :new.id from SYS.DUAL; end if; end; ;

insert into BOOKS (id) values (null)