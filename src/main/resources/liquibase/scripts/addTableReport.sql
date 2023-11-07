-- liquibase formatted sql
-- changeset elena:4
CREATE TABLE report(
    report_id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    report VARCHAR NOT NULL,
    date_time TIMESTAMP,
    checked_by_volunteer BOOLEAN
);