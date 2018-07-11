
INSERT INTO ACCOUNTS VALUES ( 1,100,FALSE);
INSERT INTO ACCOUNTS VALUES ( 2,100,FALSE);
INSERT INTO ACCOUNTS VALUES ( 3,100,FALSE);
INSERT INTO ACCOUNTS VALUES ( 4,100,FALSE);

insert into transfers ( id , from_account_id , to_account_id , amount )  values ( 1, 1, 2, 10);
insert into transfers ( id , from_account_id , to_account_id , amount )  values ( 2, 2, 1, 10);
insert into transfers ( id , from_account_id , to_account_id , amount )  values ( 3, 3, 4, 10);
insert into transfers ( id , from_account_id , to_account_id , amount )  values ( 4, 4, 3, 10);