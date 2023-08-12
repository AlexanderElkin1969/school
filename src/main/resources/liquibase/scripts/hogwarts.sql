-- liquibase formatted sql

-- changeset alex:1
CREATE UNIQUE INDEX index_student_name ON student (name);

-- changeset alex:2
CREATE UNIQUE INDEX index_faculty_name_color ON faculty (color, name);