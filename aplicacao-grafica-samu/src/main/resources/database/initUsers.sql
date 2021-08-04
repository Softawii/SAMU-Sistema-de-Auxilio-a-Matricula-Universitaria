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

