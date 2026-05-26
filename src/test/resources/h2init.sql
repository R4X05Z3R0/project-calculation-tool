DROP TABLE IF EXISTS project_employee;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS subproject;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS competency;
DROP TABLE IF EXISTS project;

CREATE TABLE project (
                         project_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         company_name VARCHAR(255) NOT NULL,
                         project_name VARCHAR(255) NOT NULL,
                         deadline DATE,
                         description VARCHAR(1000),
                         is_archived BOOLEAN DEFAULT FALSE NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
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

CREATE TABLE employee (
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

INSERT INTO project (project_id, company_name, project_name, deadline, description, is_archived, created_at) VALUES
                                                                                                                 (1, 'Acme Ltd', 'Website Redesign', '2026-06-30', 'Redesign and relaunch the company website', FALSE, '2026-05-04 13:00:00'),
                                                                                                                 (2, 'Nordic Systems', 'Internal CRM Upgrade', '2026-08-15', 'Upgrade the existing CRM platform', FALSE, '2026-05-04 13:10:00'),
                                                                                                                 (3, 'Global Logistics Inc', 'Mobile Driver App', '2026-11-01', 'Develop a mobile application for logistics tracking', FALSE, '2026-05-12 09:00:00'),
                                                                                                                 (4, 'Secure FinTech', 'Cloud Infrastructure Migration', '2026-12-20', 'Migrate on-premise servers to a secure cloud environment', TRUE, '2026-05-12 10:30:00'),
                                                                                                                 (5, 'EcoPower Energy', 'Smart Grid Dashboard', '2027-01-15', 'Real-time monitoring system for renewable energy output', FALSE, '2026-05-12 11:45:00');

INSERT INTO subproject (subproject_id, project_id, name, deadline, description) VALUES
                                                                                    (1, 1, 'Frontend Development', '2026-06-10', 'Build the public-facing frontend'),
                                                                                    (2, 1, 'Backend API', '2026-06-20', 'Create backend services and APIs'),
                                                                                    (3, 2, 'Data Migration', '2026-07-30', 'Move existing CRM data to the new system'),
                                                                                    (4, 3, 'Mobile UI/UX', '2026-09-15', 'Design and implement mobile interfaces'),
                                                                                    (5, 3, 'Geolocation Services', '2026-10-01', 'Integrate GPS and mapping features'),
                                                                                    (6, 4, 'AWS Environment Setup', '2026-11-15', 'Configure VPC, IAM, and base security layers'),
                                                                                    (7, 5, 'Data Visualization', '2026-12-01', 'Interactive charts for energy consumption'),
                                                                                    (8, 5, 'IoT Integration', '2026-12-20', 'Connect smart meters to the central dashboard');

INSERT INTO competency (competency_id, name, daily_capacity_hours, description) VALUES
                                                                                    (1, 'Frontend Developer', 6.50, 'Works with HTML, CSS, JavaScript and UI frameworks'),
                                                                                    (2, 'Backend Developer', 7.00, 'Works with databases, APIs and server-side logic'),
                                                                                    (3, 'Database Specialist', 5.50, 'Handles schema design, optimization and migration'),
                                                                                    (4, 'Mobile Developer', 6.00, 'Specializes in Flutter, React Native, or Native iOS/Android'),
                                                                                    (5, 'Cloud Architect', 7.50, 'Expert in AWS/Azure infrastructure and security'),
                                                                                    (6, 'QA Engineer', 5.00, 'Focuses on automated and manual testing protocols'),
                                                                                    (7, 'Data Scientist', 6.00, 'Analyzes large datasets and creates predictive models');

INSERT INTO task (task_id, subproject_id, competency_id, name, estimated_hours, deadline, description) VALUES
                                                                                                           (1, 1, 1, 'Create landing page', 16.00, '2026-05-20', 'Design and implement the main landing page'),
                                                                                                           (2, 1, 1, 'Build navigation menu', 8.00, '2026-05-25', 'Implement responsive navigation'),
                                                                                                           (3, 2, 2, 'Create authentication API', 20.00, '2026-06-01', 'Implement login and registration endpoints'),
                                                                                                           (4, 3, 3, 'Prepare migration script', 24.00, '2026-07-10', 'Create scripts for CRM data migration'),
                                                                                                           (5, 2, NULL, 'General project coordination', 10.00, '2026-06-05', 'Coordination task without assigned competency'),
                                                                                                           (6, 4, 4, 'User Profile Screen', 12.00, '2026-08-30', 'Develop the driver profile and settings UI'),
                                                                                                           (7, 5, 2, 'Real-time Tracking API', 30.00, '2026-09-20', 'Build the backend endpoint for location pings'),
                                                                                                           (8, 6, 5, 'VPC Configuration', 15.00, '2026-10-10', 'Set up virtual private cloud with public/private subnets'),
                                                                                                           (9, 6, 6, 'Security Audit', 10.00, '2026-11-01', 'Initial penetration testing on cloud setup'),
                                                                                                           (10, 4, NULL, 'Stakeholder Review', 4.00, '2026-09-05', 'Present mobile mockups to the client for feedback'),
                                                                                                           (11, 7, 1, 'Build D3.js Charts', 25.00, '2026-11-15', 'Create dynamic bar and line charts for energy usage'),
                                                                                                           (12, 8, 2, 'IoT Gateway Setup', 40.00, '2026-12-10', 'Establish MQTT protocol connections for smart meters'),
                                                                                                           (13, 7, 7, 'Predictive Usage Model', 35.00, '2026-11-25', 'Develop AI model to forecast peak energy demand');

INSERT INTO employee (employee_id, name, competency_id, daily_hours) VALUES
                                                                         (1, 'Alice Jensen', 1, 6.00),
                                                                         (2, 'Mikkel Hansen', 1, 5.50),
                                                                         (3, 'Sara Nielsen', 2, 7.00),
                                                                         (4, 'Jonas Berg', 2, 6.50),
                                                                         (5, 'Nora Petersen', 3, 5.00),
                                                                         (6, 'Emil Larsen', 4, 6.00),
                                                                         (7, 'Freja Holm', 5, 7.50),
                                                                         (8, 'Oscar Madsen', 6, 5.00),
                                                                         (9, 'Ida Sørensen', 7, 6.00);

ALTER TABLE project ALTER COLUMN project_id RESTART WITH 6;
ALTER TABLE subproject ALTER COLUMN subproject_id RESTART WITH 9;