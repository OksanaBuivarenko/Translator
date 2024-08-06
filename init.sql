CREATE SCHEMA IF NOT EXISTS translator_schema;

CREATE TABLE IF NOT EXISTS translator_schema.requests
(
    id SERIAL  PRIMARY KEY,
    address_ip VARCHAR(255) NOT NULL,
    start_text VARCHAR(255) NOT NULL,
    end_text VARCHAR(255) NOT NULL
);