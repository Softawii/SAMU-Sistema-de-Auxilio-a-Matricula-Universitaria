CREATE TABLE IF NOT EXISTS Student
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR UNIQUE,
    password VARCHAR,
    name     VARCHAR,
    address  VARCHAR,
    courses  VARCHAR
);

INSERT INTO Student (username, password, name, address, courses)
VALUES ('Eduardo', '1234', 'Meu Nome', 'Meu endereço', 'DCC00'),
       ('Yan', '1234', 'Meu Nome', 'Meu endereço', 'DCC01'),
       ('Romulo', '1234', 'Meu Nome', 'Meu endereço', 'DCC02'),
       ('Victor', '1234', 'Meu Nome', 'Meu endereço', 'DMT00'),
       ('Matheus', '1234', 'Meu Nome', 'Meu endereço', 'DMT02');