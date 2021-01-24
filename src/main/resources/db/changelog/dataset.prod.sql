--liquibase formatted sql

--changeset tolkiana:4
INSERT INTO sizes (id, code, sort_order) VALUES (1, "X-SMALL", 1);
INSERT INTO sizes (id, code, sort_order) VALUES (2, "SMALL", 2);
INSERT INTO sizes (id, code, sort_order) VALUES (3, "MEDIUM", 3);
INSERT INTO sizes (id, code, sort_order) VALUES (4, "LARGE", 4);

INSERT INTO colors (id, code, name) VALUES (1, "30B2CF", "Blue");
INSERT INTO colors (id, code, name) VALUES (2, "27C34D", "Green");
INSERT INTO colors (id, code, name) VALUES (3, "C33327", "Red");
INSERT INTO colors (id, code, name) VALUES (4, "F1F147", "Yellow");
