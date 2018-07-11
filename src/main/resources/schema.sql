CREATE TABLE ACCOUNTS
(
    ID BIGINT not null,
    BALANCE DOUBLE not null,
    DELETED BOOLEAN not null DEFAULT(FALSE),
    PRIMARY KEY (ID)
);

CREATE TABLE TRANSFERS
(
    ID BIGINT not null,
    FROM_ACCOUNT_ID BIGINT,
    TO_ACCOUNT_ID BIGINT,
    AMOUNT DOUBLE not null,
    EXECUTED VARCHAR(1),
    PRIMARY KEY (ID),
    FOREIGN KEY (FROM_ACCOUNT_ID) REFERENCES ACCOUNTS(ID),
    FOREIGN KEY (TO_ACCOUNT_ID) REFERENCES ACCOUNTS(ID)
);

CREATE sequence ACCOUNTS_SEQ start with 1000;
CREATE sequence TRANSFERS_SEQ start with 1000;