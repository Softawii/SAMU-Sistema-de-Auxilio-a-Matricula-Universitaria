CREATE TABLE IF NOT EXISTS Student
(
    id                INTEGER UNIQUE,
    requestedLectures VARCHAR,
    enrollLectures    VARCHAR,
    course            VARCHAR,
    semester          VARCHAR
);
