CREATE TABLE DROOLS_PACKAGE
(
    id BIGINT IDENTITY (1,1) NOT NULL,
    name VARCHAR(255)       NOT NULL,
    sab_kopija BLOB         NOT NULL,
    CONSTRAINT PK__drools_package_id primary key (id)
)