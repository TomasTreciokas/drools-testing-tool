CREATE TABLE item
(
    id   BIGINT IDENTITY (1,1) NOT NULL,
    type VARCHAR(255)          NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT PK__item_id primary key (id)
);