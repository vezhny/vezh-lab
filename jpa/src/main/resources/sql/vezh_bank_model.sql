CREATE TABLE CARDS
(
  CARD_ID int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  PAN TINYTEXT NOT NULL,
  AMOUNT LONG NOT NULL,
  HOLDER_ID int NOT NULL,
  CREATION_DATE DATE NOT NULL,
  CVC int NOT NULL,
  EXPIRY int NOT NULL,
  CURRENCY int NOT NULL,
  CARD_STATUS varchar(20) NOT NULL,
  BRAND_ID int NOT NULL
);
CREATE UNIQUE INDEX cards_CARD_ID_uindex ON cards (CARD_ID);
CREATE UNIQUE INDEX cards_PAN_uindex ON cards (PAN);

CREATE TABLE CURRENCIES
(
    CODE int PRIMARY KEY NOT NULL,
    CURRENCY_VALUE VARCHAR(3) NOT NULL
);
CREATE UNIQUE INDEX CURRENCIES_CODE_uindex ON CURRENCIES (CODE);
CREATE UNIQUE INDEX CURRENCIES_VALUE_uindex ON CURRENCIES (CUREENCY_VALUE);

CREATE TABLE USER_REQUESTS
(
    REQUEST_ID int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    USER_ID int NOT NULL,
    CREATION_DATE DATE NOT NULL,
    REQUEST_STATUS varchar(20) NOT NULL,
    REQUEST_DATA VARCHAR(150) NOT NULL
);
CREATE UNIQUE INDEX USER_REQUESTS_REQUEST_ID_uindex ON USER_REQUESTS (REQUEST_ID);

CREATE TABLE USER_ROLES
(
    ROLE_ID int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    ROLE_NAME VARCHAR(20) NOT NULL
);
CREATE UNIQUE INDEX USER_ROLES_ROLE_ID_uindex ON USER_ROLES (ROLE_ID);
CREATE UNIQUE INDEX USER_ROLES_ROLE_NAME_uindex ON USER_ROLES (ROLE_NAME);

CREATE TABLE TRANSACTIONS
(
    TRX_ID int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    TRX_TYPE VARCHAR(30) NOT NULL,
    DATE_TIME DATE NOT NULL,
    TRX_DATA VARCHAR(150) NOT NULL,
    TRX_STATUS VARCHAR(20) NOT NULL
);
CREATE UNIQUE INDEX TRANSACTIONS_TRX_ID_uindex ON TRANSACTIONS (TRX_ID);

CREATE TABLE USERS
(
    USER_ID int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    LOGIN VARCHAR(30) NOT NULL,
    USER_PASSWORD VARCHAR(30) NOT NULL,
    ROLE_ID int NOT NULL,
    CONFIG VARCHAR(150) NOT NULL,
    ATTEMPTS_TO_SIGN_IN_LEFT int NOT NULL,
    BLOCKED TINYINT NOT NULL,
    LAST_SIGN_IN_DATE date,
    USER_DATA VARCHAR(200) NOT NULL
);
CREATE UNIQUE INDEX USERS_USER_ID_uindex ON USERS (USER_ID);
CREATE UNIQUE INDEX USERS_LOGIN_uindex ON USERS (LOGIN);

CREATE TABLE EVENTS
(
    EVENT_ID int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    EVENT_TYPE VARCHAR(30) NOT NULL,
    EVENT_DATA varchar(50) NOT NULL,
    EVENT_DATE date NOT NULL
);
CREATE UNIQUE INDEX EVENTS_EVENT_ID_uindex ON EVENTS (EVENT_ID);