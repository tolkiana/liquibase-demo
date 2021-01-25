--liquibase formatted sql

--changeset tolkiana:3
INSERT INTO sizes (code, sort_order) VALUES ('X-SMALL', 1);
INSERT INTO sizes (code, sort_order) VALUES ('SMALL', 2);
INSERT INTO sizes (code, sort_order) VALUES ('MEDIUM', 3);
INSERT INTO sizes (code, sort_order) VALUES ('LARGE', 4);

INSERT INTO colors (code, name) VALUES ('30B2CF', 'Blue');
INSERT INTO colors (code, name) VALUES ('27C34D', 'Green');
INSERT INTO colors (code, name) VALUES ('C33327', 'Red');
INSERT INTO colors (code, name) VALUES ('F1F147', 'Yellow');
