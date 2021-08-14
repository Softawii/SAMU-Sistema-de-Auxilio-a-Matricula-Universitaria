CREATE TABLE IF NOT EXISTS Teachers
(
    id       INTEGER UNIQUE,
    lectures VARCHAR,
    course   VARCHAR
);

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

CREATE TABLE IF NOT EXISTS Student
(
    id                INTEGER UNIQUE,
    requestedLectures VARCHAR,
    enrollLectures    VARCHAR,
    course            VARCHAR,
    semester          VARCHAR
);

CREATE TABLE IF NOT EXISTS Users
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR UNIQUE,
    password VARCHAR,
    name     VARCHAR,
    cpf      VARCHAR UNIQUE,
    address  VARCHAR,
    birthday VARCHAR,
    type     VARCHAR
);

CREATE TABLE IF NOT EXISTS Subjects
(
    code          VARCHAR UNIQUE,
    name          VARCHAR,
    description   VARCHAR,
    prerequisites VARCHAR
);