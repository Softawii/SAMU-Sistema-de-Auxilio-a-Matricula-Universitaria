CREATE TABLE IF NOT EXISTS Subjects
(
    code          VARCHAR UNIQUE,
    name          VARCHAR,
    description   VARCHAR,
    prerequisites VARCHAR,
    schedule      VARCHAR
);

INSERT INTO Subjects (code, name, description, prerequisites, schedule)
VALUES ('DCC05', 'Circuitos Digitais', ' - ', '', 'Meio dia para as 10h'),
       ('DCC00', 'Circuitos Digitais', ' - ', '', 'Meio dia para as 10h'),
       ('DCC01', 'Arquitetura de Computadores I', ' - ', 'DCC00,DCC05', 'Meio dia para as 10h'),
       ('DCC02', 'Arquitetura de Computadores II', ' - ', 'DCC02', 'Meio dia para as 10h'),
       ('DMT00', 'Calculo I', ' - ', '', 'Meio dia para as 10h'),
       ('DMT01', 'Calculo II', ' - ', 'DMT00', 'Meio dia para as 10h'),
       ('DMT02', 'Calculo Aplicado', ' - ', 'DMT01', 'Meio dia para as 10h');
