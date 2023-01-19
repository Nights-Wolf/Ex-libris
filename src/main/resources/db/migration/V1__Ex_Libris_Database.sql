CREATE TABLE user (
    id SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    name VARCHAR(20),
    surname VARCHAR(20)
);