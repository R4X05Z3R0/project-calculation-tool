CREATE DATABASE IF NOT EXISTS aspect;
USE aspect;

DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS subproject;
DROP TABLE IF EXISTS competency;
DROP TABLE IF EXISTS project;

CREATE TABLE project (
                         project_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         company_name VARCHAR(255) NOT NULL,
                         project_name VARCHAR(255) NOT NULL,
                         deadline DATE,
                         description VARCHAR(1000),
                         is_archived BOOLEAN DEFAULT FALSE NOT NULL, -- Adding archive field
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