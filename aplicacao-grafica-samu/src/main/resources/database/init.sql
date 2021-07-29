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
VALUES ('Eduardo', '1234', 'Meu Nome', 'Meu endereço', 'T1, T2, T3'),
       ('Yan', '1234', 'Meu Nome', 'Meu endereço', 'T1, T2, T3'),
       ('Romulo', '1234', 'Meu Nome', 'Meu endereço', 'T1, T2, T3'),
       ('Victor', '1234', 'Meu Nome', 'Meu endereço', 'T1, T2, T3'),
       ('Matheus', '1234', 'Meu Nome', 'Meu endereço', 'T1, T2, T3');