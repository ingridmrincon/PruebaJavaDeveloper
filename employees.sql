-- Database: employees

-- DROP DATABASE employees;

CREATE DATABASE employees
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
	-- SCHEMA: employee

-- DROP SCHEMA employee ;

CREATE SCHEMA employee
    AUTHORIZATION postgres;
	

-- Table: employee.employee

-- DROP TABLE employee.employee;

CREATE TABLE employee.employee
(
    idemployee bigint NOT NULL DEFAULT nextval('employee.empleado_idempleado_seq'::regclass),
    name character varying(50) COLLATE pg_catalog."default",
    lastname character varying(50) COLLATE pg_catalog."default",
    identificationtype character varying(50) COLLATE pg_catalog."default",
    identification character varying(50) COLLATE pg_catalog."default",
    birthdate date,
    phonenumber numeric(12,0),
    profession character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT empleado_pkey PRIMARY KEY (idemployee)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE employee.employee
    OWNER to postgres;


-- Table: employee.salaryemployee

-- DROP TABLE employee.salaryemployee;

CREATE TABLE employee.salaryemployee
(
    idsalary bigint NOT NULL DEFAULT nextval('employee.salarioempleado_idsalary_seq'::regclass),
    "position" character varying(50) COLLATE pg_catalog."default",
    salary numeric(8,0),
    idemployee bigint NOT NULL DEFAULT nextval('employee.salarioempleado_employeeoid_seq'::regclass),
    CONSTRAINT salarioempleado_pkey PRIMARY KEY (idsalary),
    CONSTRAINT salaryemployee_idemployee_fkey FOREIGN KEY (idemployee)
        REFERENCES employee.employee (idemployee) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT salaryemployee_idemployee_fkey1 FOREIGN KEY (idemployee)
        REFERENCES employee.employee (idemployee) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE employee.salaryemployee
    OWNER to postgres;