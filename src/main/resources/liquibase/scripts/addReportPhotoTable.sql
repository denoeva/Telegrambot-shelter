-- liquibase formatted sql
-- changeset elena:5
CREATE TABLE report_photo(
      id BIGSERIAL PRIMARY KEY,
      report_photo_data bytea NOT NULL,
      report_id BIGINT references report(report_id)
);