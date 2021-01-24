--liquibase formatted sql

--changeset tolkiana:1
CREATE TABLE sizes (
    id SERIAL PRIMARY KEY,
    code VARCHAR,
    sort_order INTEGER
);

CREATE TABLE colors (
    id SERIAL PRIMARY KEY,
    code VARCHAR,
    name VARCHAR
);

--changeset tolkiana:2
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    description VARCHAR,
    size_id INTEGER NOT NULL,
    FOREIGN KEY (size_id) REFERENCES sizes (id)
);

CREATE TABLE product_colors (
    product_id INTEGER NOT NULL,
    color_id INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (color_id) REFERENCES colors (id)
);
