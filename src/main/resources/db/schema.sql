CREATE DATABASE IF NOT EXISTS aspect;
USE aspect;

DROP TABLE IF EXISTS project_employee;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS subproject;
DROP TABLE IF EXISTS competency;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS employee;


CREATE TABLE project (
    project_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    project_name VARCHAR(255) NOT NULL,
    deadline DATE,
    description VARCHAR(1000),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE subproject (
    subproject_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    deadline DATE,
    description VARCHAR(1000),

    CONSTRAINT fk_subproject_project
        FOREIGN KEY (project_id)
        REFERENCES project(project_id)
        ON DELETE CASCADE
);

CREATE TABLE competency (
    competency_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    daily_capacity_hours DECIMAL(5,2) NOT NULL,
    description VARCHAR(1000)
);

CREATE TABLE task (
    task_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subproject_id BIGINT NOT NULL,
    competency_id BIGINT NULL,
    name VARCHAR(255) NOT NULL,
    estimated_hours DECIMAL(8,2) NOT NULL,
    deadline DATE,
    description VARCHAR(1000),

    CONSTRAINT fk_task_subproject
        FOREIGN KEY (subproject_id)
        REFERENCES subproject(subproject_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_task_competency
        FOREIGN KEY (competency_id)
        REFERENCES competency(competency_id)
        ON DELETE SET NULL
);

Create TABLE employee (
                          employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(55) NOT NULL,
                          competency_id BIGINT NOT NULL,
                          daily_hours DECIMAL(5,2) NOT NULL,

CONSTRAINT fk_employee_competency
        FOREIGN KEY (competency_id)
        REFERENCES competency(competency_id)

);

CREATE TABLE project_employee (
                                  project_id BIGINT NOT NULL,
                                  employee_id BIGINT NOT NULL,

                                  PRIMARY KEY (project_id, employee_id),

                                  CONSTRAINT fk_project_employee_project
                                      FOREIGN KEY (project_id)
                                          REFERENCES project(project_id)
                                          ON DELETE CASCADE,

                                  CONSTRAINT fk_project_employee_employee
                                      FOREIGN KEY (employee_id)
                                          REFERENCES employee(employee_id)
                                          ON DELETE CASCADE
);