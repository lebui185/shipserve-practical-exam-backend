CREATE TABLE company_type_lookup (
    id SERIAL,
    value VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE company_type_lookup
ADD CONSTRAINT unique__company_type_lookup__value UNIQUE (value);

INSERT INTO company_type_lookup VALUES (1, 'Buyer');
INSERT INTO company_type_lookup VALUES (2, 'Supplier');

CREATE TABLE company (
    id SERIAL,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    type_id INT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

ALTER TABLE company
ADD CONSTRAINT fk__company__type_id
FOREIGN KEY (type_id) REFERENCES company_type_lookup(id);

CREATE TABLE employee (
    id SERIAL,
    first_name VARCHAR(255) NULL,
    surname VARCHAR(255) NULL,
    email VARCHAR(255) NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    company_id INT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    
    PRIMARY KEY (id)
);

ALTER TABLE employee
ADD CONSTRAINT fk__employee__company_id
FOREIGN KEY (company_id) REFERENCES company(id);

CREATE TABLE role (
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    
    PRIMARY KEY (id)
);

ALTER TABLE role
ADD CONSTRAINT unique__role__name UNIQUE (name);

INSERT INTO role (id, name) VALUES (1, 'SUPER_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'COMPANY_ADMIN');
INSERT INTO role (id, name) VALUES (3, 'USER');

CREATE TABLE employee_role (
    employee_id INT NOT NULL,
    role_id INT NOT NULL,
    
    PRIMARY KEY (employee_id, role_id)
);

ALTER TABLE employee_role
ADD CONSTRAINT fk__employee_role__emplooyee_id
FOREIGN KEY (employee_id) REFERENCES employee(id);

ALTER TABLE employee_role
ADD CONSTRAINT fk__employee_role__role_id
FOREIGN KEY (role_id) REFERENCES role(id);