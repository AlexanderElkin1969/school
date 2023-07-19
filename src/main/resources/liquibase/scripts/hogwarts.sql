-- liquibase formatted sql

-- changeset alex:1
ALTER TABLE student ALTER COLUMN age SET DEFAULT (16);

-- changeset alex:2
CREATE INDEX index_student_name ON student (name);

-- changeset alex:3
CREATE INDEX index_faculty_name_color ON faculty (color, name);