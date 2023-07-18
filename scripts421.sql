ALTER TABLE student ADD CONSTRAINT age_constraint CHECK ( age >= 12 );
ALTER TABLE student ADD PRIMARY KEY (name);
ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;
ALTER TABLE faculty ADD CONSTRAINT name_color_constraint UNIQUE (name, color);