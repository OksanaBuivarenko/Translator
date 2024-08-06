CREATE SCHEMA IF NOT EXISTS translator_schema;

CREATE TABLE IF NOT EXISTS translator_schema.requests
(
    id SERIAL  PRIMARY KEY,
    address_ip VARCHAR(255) NOT NULL,
    start_text VARCHAR(255) NOT NULL,
    end_text VARCHAR(255) NOT NULL
);

insert into requests (id, address_ip, start_text, end_text)
values (1, '0:0:0:0:0:0:0:1', 'Кот ест снег', 'Cat eats snow');
insert into requests (id, address_ip, start_text, end_text)
values (2, '0:0:0:0:0:0:0:2', 'Привет мир, это моя первая программа', 'Hello world,this is my first program');

SELECT setval ('requests_id_seq', (SELECT MAX(id) FROM requests));