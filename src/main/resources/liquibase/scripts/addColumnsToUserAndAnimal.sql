-- liquibase formatted sql
-- changeset dschulpanova:3

alter table users add column animal_id BIGINT;
alter table users add constraint users_fk_animal_id foreign key (animal_id) references animal(id);