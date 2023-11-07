-- liquibase formatted sql
-- changeset elena:5
CREATE TABLE report_photo(
      id BIGSERIAL PRIMARY KEY,
      report_photo_file_path VARCHAR NOT NULL,
      report_photo_file_size BIGINT NOT NULL,
      report_photo_media_type VARCHAR NOT NULL,
      report_photo_data bytea NOT NULL,
      animal_id BIGSERIAL NOT NULL
  );