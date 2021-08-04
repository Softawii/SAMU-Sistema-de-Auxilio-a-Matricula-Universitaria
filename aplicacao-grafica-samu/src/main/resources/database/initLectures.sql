CREATE TABLE IF NOT EXISTS Lectures
(
    code      VARCHAR UNIQUE,
    schedule  VARCHAR,
    classRoom VARCHAR,
    classPlan VARCHAR,
    subject   VARCHAR
);

INSERT INTO Lectures (code, schedule, classRoom, classPlan, subject)
VALUES ('ICC20202', 'Meio dia pras 10',  ' - ', 'null',              'DCC05'),
       ('CD20202' , 'Meio dia pras 10',  ' - ', 'null',              'DCC00'),
       ('AC120202', 'Meio dia pras 10',  ' - ', 'null',              'DCC01'),
       ('AC220202', 'Meio dia pras 10',  ' - ', 'null',              'DCC02'),
       ('C120202' , 'Meio dia pras 10',  ' - ', 'null',              'DMT01'),
       ('C20202'  , 'Meio dia pras 10',  ' - ', 'null',              'DMT00'),
       ('CA20202' , 'Meio dia pras 10',  ' - ', 'null',              'DMT02');
