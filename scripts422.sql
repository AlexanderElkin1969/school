CREATE TABLE cars (
    id BIGSERIAL PRIMARY KEY ,
    brand VARCHAR(16) NOT NULL ,
    model VARCHAR(16) NOT NULL ,
    price INTEGER CHECK ( price > 0 ) NOT NULL
);

CREATE TABLE humans (
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(16) NOT NULL ,
    age INTEGER CHECK ( age > 0 ) NOT NULL ,
    carLicense VARCHAR(16),
    car_id BIGINT REFERENCES cars(id) NOT NULL
)
/* если carLicense == NULL, то человек - пассажир авто
