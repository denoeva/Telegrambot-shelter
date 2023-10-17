-- liquibase formatted sql
-- changeset elena:1

CREATE TABLE animals(
    id BIGSERIAL PRIMARY KEY,
    typeOfAnimal VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    breed VARCHAR,
    color VARCHAR NOT NULL,
    DOB DATE NOT NULL,
    health VARCHAR,
    characteristic VARCHAR,
    attached BOOLEAN
);

CREATE TABLE photo(
    id BIGSERIAL PRIMARY KEY,
    filePath VARCHAR NOT NULL,
    fileSize BIGINT NOT NULL,
    media_type VARCHAR NOT NULL
);

Create TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR,
    chat_id BIGINT NOT NULL,
    phoneNumber VARCHAR,
    attached BOOLEAN
);

CREATE TABLE volunteers(
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    name VARCHAR NOT NULL
);