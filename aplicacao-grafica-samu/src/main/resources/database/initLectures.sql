CREATE TABLE IF NOT EXISTS Lectures
(
    code      VARCHAR UNIQUE,
    schedule  VARCHAR,
    classRoom VARCHAR,
    classPlan VARCHAR,
    subject   VARCHAR,
    teacher   INTEGER,
    students  VARCHAR
);

INSERT INTO Lectures (code, schedule, classRoom, classPlan, subject, teacher, students)
VALUES ('ICC20202', 'Meio dia pras 10',  ' - ', 'null',              'DCC05', 1, ''),
       ('CD20202' , 'Meio dia pras 10',  ' - ', 'null',              'DCC00', 1, ''),
       ('AC120202', 'Meio dia pras 10',  ' - ', 'null',              'DCC01', 1, ''),
       ('AC220202', 'Meio dia pras 10',  ' - ', 'null',              'DCC02', 1, ''),
       ('C120202' , 'Meio dia pras 10',  ' - ', 'null',              'DMT01', 1, ''),
       ('C20202'  , 'Meio dia pras 10',  ' - ', 'null',              'DMT00', 1, ''),
       ('CA20202' , 'Meio dia pras 10',  ' - ', 'null',              'DMT02', 1, '');
