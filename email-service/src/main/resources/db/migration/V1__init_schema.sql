-- V1__init_schema.sql

-- Email TABLE
CREATE TABLE emails
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    to_email        VARCHAR(100)                        NOT NULL,
    subject         VARCHAR(100)                        NOT NULL,
    body            TEXT,
    attachment_path VARCHAR(255),
    template        VARCHAR(100),
    model           JSONB,
    send_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);