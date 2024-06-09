--liquibase formatted sql

--changeset raivis:1

CREATE TABLE flight
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    from_airport_id INT          NOT NULL,
    to_airport_id   INT          NOT NULL,
    carrier         VARCHAR(255) NOT NULL,
    departure_time  TIMESTAMP    NOT NULL,
    arrival_time    TIMESTAMP    NOT NULL,
    CONSTRAINT fk_from_airport FOREIGN KEY (from_airport_id) REFERENCES airport(id),
    CONSTRAINT fk_to_airport FOREIGN KEY (to_airport_id) REFERENCES airport(id),
    CONSTRAINT unique_flight UNIQUE (from_airport_id, to_airport_id, carrier, departure_time, arrival_time)
);