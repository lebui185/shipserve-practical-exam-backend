INSERT INTO company (name, address, type_id) VALUES ('Company 1', 'Address 1', '1');
INSERT INTO company (name, address, type_id) VALUES ('Company 2', 'Address 2', '2');

-- password: Abcd@1234
INSERT INTO employee (first_name, surname, email, username, password, company_id)
VALUES ('Super', 'Admin', 'jon.snow@themail.com', 'admin', '$2a$10$.B1iXNmQ6.PZ3IKbVFPmVen5TqIxk0q1vgTFvQ2pPPBXfItSaKY76', null);
INSERT INTO employee_role (employee_id, role_id) VALUES (1, 1);

INSERT INTO employee (first_name, surname, email, username, password, company_id)
VALUES ('Jon', 'Snow', 'jon.snow@themail.com', 'cadmin1', '$2a$10$.B1iXNmQ6.PZ3IKbVFPmVen5TqIxk0q1vgTFvQ2pPPBXfItSaKY76', 1);
INSERT INTO employee_role (employee_id, role_id) VALUES (2, 2);

INSERT INTO employee (first_name, surname, email, username, password, company_id)
VALUES ('Tomi', 'Farrington', 'tomi.farrington@themail.com', 'user1', '$2a$10$.B1iXNmQ6.PZ3IKbVFPmVen5TqIxk0q1vgTFvQ2pPPBXfItSaKY76', 1);
INSERT INTO employee_role (employee_id, role_id) VALUES (3, 3);

INSERT INTO employee (first_name, surname, email, username, password, company_id)
VALUES ('Arnas', 'Gray', 'arnas.gray@themail.com', 'cadmin2', '$2a$10$.B1iXNmQ6.PZ3IKbVFPmVen5TqIxk0q1vgTFvQ2pPPBXfItSaKY76', 2);
INSERT INTO employee_role (employee_id, role_id) VALUES (4, 2);

INSERT INTO employee (first_name, surname, email, username, password, company_id)
VALUES ('Alysia', 'Williams', 'alysia.williams@themail.com', 'user2', '$2a$10$.B1iXNmQ6.PZ3IKbVFPmVen5TqIxk0q1vgTFvQ2pPPBXfItSaKY76', 2);
INSERT INTO employee_role (employee_id, role_id) VALUES (5, 3);