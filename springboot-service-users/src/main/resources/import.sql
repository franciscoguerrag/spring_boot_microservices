INSERT INTO users (username, password, enabled, name, last_name, email) VALUES ('Andres', '$2a$10$VhA3aU3YlyaDnaVx0cxPFeYU2sNFP10tSNtsiCQ6BeDR2Vhk.QrTO', true, 'Andres', 'Perez', 'andresperez@empresa.com');
INSERT INTO users (username, password, enabled, name, last_name, email) VALUES ('John', '$2a$10$9DJl3XiheYIMWh9kODFApu.mCbrPrAgBpNSLHAge3z0VOmYFo2YtW', true, 'John', 'Guzman', 'jonguzman@empresa.com');
INSERT INTO users (username, password, enabled, name, last_name, email) VALUES ('admin', '$2a$10$8PG0cVjJQu0yfnGcfmm3pOoxPP0sMpkpFQ3tiKAaFOptoA6mm9cXO', true, 'Camilo', 'Dickens', 'camilodickens@empresa.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1,1);
INSERT INTO users_roles (user_id, role_id) VALUES (2,1);
INSERT INTO users_roles (user_id, role_id) VALUES (3,2);
INSERT INTO users_roles (user_id, role_id) VALUES (3,1);
