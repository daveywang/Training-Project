DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS role_id_seq;

CREATE SEQUENCE user_id_seq START WITH 1;
CREATE SEQUENCE role_id_seq START WITH 1;


DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS role CASCADE;


CREATE TABLE users (
    id              SERIAL NOT NULL,
    name            VARCHAR(30) not null unique,
    password        VARCHAR(64),
    secret_key      varchar(512),
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50) not null unique
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

CREATE TABLE role (
    id                   SERIAL NOT NULL,
    name                 VARCHAR(30) not null unique,
    allowed_resource     VARCHAR(200),
    allowed_read         VARCHAR(1) not null default 'N',
    allowed_create       VARCHAR(1) not null default 'N',
    allowed_update       VARCHAR(1) not null default 'N',
    allowed_delete       VARCHAR(1) not null default 'N'
);

ALTER TABLE role ADD CONSTRAINT role_pk PRIMARY KEY ( id );

CREATE TABLE users_role (
    user_id    INTEGER NOT NULL,
    role_id    INTEGER NOT NULL
);

ALTER TABLE users_role
    ADD CONSTRAINT users_fk FOREIGN KEY ( user_id )
        REFERENCES users ( id );

ALTER TABLE users_role
    ADD CONSTRAINT role_fk FOREIGN KEY ( role_id )
        REFERENCES role ( id );
