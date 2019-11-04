DROP TABLE IF EXISTS department CASCADE;
DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS account CASCADE;
--DROP SEQUENCE IF EXISTS department_id_seq;
--DROP SEQUENCE IF EXISTS employee_id_seq;
--DROP SEQUENCE IF EXISTS account_id_seq;

-- CREATE SEQUENCE department_id_seq START WITH 1;
-- CREATE SEQUENCE employee_id_seq START WITH 1;
-- CREATE SEQUENCE account_id_seq START WITH 1;


CREATE TABLE department (
    /*id                INTEGER NOT NULL default nextval('department_id_seq'), */
    id                SERIAL NOT NULL,
    name              VARCHAR(30) not null unique,
    description       VARCHAR(150),
    location          VARCHAR(100)
);

ALTER TABLE department ADD CONSTRAINT department_pk PRIMARY KEY ( id );

CREATE TABLE employee (
    /*id              INTEGER NOT NULL default nextval('employee_id_seq'),*/
    id              SERIAL NOT NULL,
    name            VARCHAR(30) not null unique,
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50),
    address         VARCHAR(150),
    hired_date      date default CURRENT_DATE,
    department_id   INTEGER NOT NULL
);

ALTER TABLE employee ADD CONSTRAINT employee_pk PRIMARY KEY ( id );

CREATE TABLE account (
    /*id             INTEGER NOT NULL default nextval('account_id_seq'),*/
    id             SERIAL NOT NULL,
    account_type   VARCHAR(30),
    balance        FLOAT,
    create_date    date default CURRENT_DATE,
    employee_id    INTEGER NOT NULL
);

ALTER TABLE account ADD CONSTRAINT account_pk PRIMARY KEY ( id );

ALTER TABLE account
    ADD CONSTRAINT account_employee_fk FOREIGN KEY ( employee_id )
        REFERENCES employee ( id );

ALTER TABLE employee
    ADD CONSTRAINT employee_department_fk FOREIGN KEY ( department_id )
        REFERENCES department ( id );

CREATE TABLE users (
    name            VARCHAR(30) NOT NULL PRIMARY KEY,
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50)
);