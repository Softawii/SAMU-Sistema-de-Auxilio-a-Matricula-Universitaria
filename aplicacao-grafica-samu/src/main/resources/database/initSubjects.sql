CREATE TABLE IF NOT EXISTS Subjects
(
    code          VARCHAR UNIQUE,
    name          VARCHAR,
    description   VARCHAR,
    prerequisites VARCHAR
);

INSERT INTO Subjects (code, name, description, prerequisites)
VALUES ('DCC05', 'ICC',                             ' - ', ''               ),
       ('DCC00', 'Circuitos Digitais',              ' - ', ''               ),
       ('DCC01', 'Arquitetura de Computadores I',   ' - ', 'DCC00,DCC05'    ),
       ('DCC02', 'Arquitetura de Computadores II',  ' - ', 'DCC02'          ),
       ('DMT00', 'Calculo I',                       ' - ', ''               ),
       ('DMT01', 'Calculo II',                      ' - ', 'DMT00'          ),
       ('DMT02', 'Calculo Aplicado',                ' - ', 'DMT01'          );
