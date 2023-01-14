DROP TABLE persons IF EXISTS;
CREATE TABLE persons(
    id BIGINT NOT NULL,
    last_name VARCHAR(15),
    first_name VARCHAR(15),
    number_of_dependants INT,
    PRIMARY KEY (id)
);