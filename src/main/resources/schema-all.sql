DROP TABLE products IF EXISTS;

CREATE TABLE products  (
    product_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(20),
    qty DOUBLE,
    price DOUBLE,
    description VARCHAR(20),
    total DOUBLE
);