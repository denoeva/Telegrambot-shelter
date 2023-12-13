-- liquibase formatted sql
-- changeset elena:4
CREATE TABLE report(
    report_id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    report VARCHAR NOT NULL,
    date_time TIMESTAMP,
    checked_by_volunteer BOOLEAN,
    animal_id BIGSERIAL NOT NULL
);

-- changeset elena:6
ALTER TABLE report
RENAME COLUMN report TO report_text