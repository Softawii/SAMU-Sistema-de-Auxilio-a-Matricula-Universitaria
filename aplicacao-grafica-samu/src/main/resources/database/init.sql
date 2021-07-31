CREATE TABLE IF NOT EXISTS Student
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR UNIQUE,
    password VARCHAR,
    name     VARCHAR,
    address  VARCHAR,
    subjects VARCHAR,
    course   VARCHAR,
    semester VARCHAR
);

INSERT INTO Student (username, password, name, address, subjects, course, semester)
VALUES ('eduardo', '1234', 'Eduardo', 'Meu endereco', 'DCC00', 'Ciencia de Computacao', '2019.1'),
       ('yan', '1234', 'Yan', 'Meu endereco', 'DCC01,DCC02,DMT01', 'Ciencia de Computacao', '2019.1'),
       ('romulo', '1234', 'Romulo', 'Meu endereco', 'DCC02', 'Ciencia de Computacao', '2019.1'),
       ('victor', '1234', 'Victor', 'Meu endereco', 'DMT00', 'Ciencia de Computacao', '2019.1'),
       ('matheus', '1234', 'Matheus', 'Meu endereco', 'DMT02', 'Turismo', '2019.1');