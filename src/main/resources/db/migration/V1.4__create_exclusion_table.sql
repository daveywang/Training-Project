DROP TABLE IF EXISTS property_exclusion CASCADE;

CREATE TABLE property_exclusion (
    id                  SERIAL NOT NULL,
    class_name          VARCHAR(50) not null,
    excluded_properties VARCHAR(512),
    role_id             INTEGER NOT NULL
);

ALTER TABLE property_exclusion ADD CONSTRAINT exclusion_pk PRIMARY KEY ( id );

ALTER TABLE property_exclusion
    ADD CONSTRAINT role_fk FOREIGN KEY ( role_id )
        REFERENCES role ( id );

ALTER TABLE property_exclusion
    ADD CONSTRAINT role_id_class_name UNIQUE (role_id, class_name)