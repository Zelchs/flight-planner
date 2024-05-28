--liquibase formatted sql

--changeset raivis:1

CREATE TABLE airport
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    country VARCHAR(255) NOT NULL,
    city    VARCHAR(255) NOT NULL,
    airport VARCHAR(255) NOT NULL UNIQUE
);


