--liquibase formatted sql
--changeSet abed:002-01

CREATE TABLE users
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username       VARCHAR(50) NOT NULL,
    register_price DECIMAL     NOT NULL,
    coin_id        BIGINT      NOT NULL REFERENCES coins (id)
);

-- rollback DROP TABLE users