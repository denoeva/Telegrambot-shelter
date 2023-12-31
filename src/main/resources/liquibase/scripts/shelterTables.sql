-- liquibase formatted sql
-- changeset elena:1

CREATE TABLE animal(
    id BIGSERIAL PRIMARY KEY,
    type_of_animal INTEGER NOT NULL,
    shelter_id BIGSERIAL NOT NULL,
    name VARCHAR NOT NULL,
    breed VARCHAR,
    gender VARCHAR,
    color VARCHAR NOT NULL,
    DOB DATE NOT NULL,
    health VARCHAR,
    characteristic VARCHAR,
    attached BOOLEAN
);

CREATE TABLE photo(
      id BIGSERIAL PRIMARY KEY,
      file_path VARCHAR NOT NULL,
      file_size BIGINT NOT NULL,
      media_type VARCHAR NOT NULL,
      data bytea NOT NULL,
      animal_id BIGSERIAL NOT NULL
  );

Create TABLE users(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR,
    chat_id BIGINT NOT NULL,
    phone_number VARCHAR,
    attached BOOLEAN
);

CREATE TABLE volunteer(
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    name VARCHAR NOT NULL
);

-- changeset anton:2
ALTER TABLE animal
DROP COLUMN shelter_id;