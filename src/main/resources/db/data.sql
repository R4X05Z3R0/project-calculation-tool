INSERT INTO project (
    project_id,
    company_name,
    project_name,
    deadline,
    description,
    created_at
) VALUES
(1, 'Acme Ltd', 'Website Redesign', '2026-06-30', 'Redesign and relaunch the company website', '2026-05-04 13:00:00'),
(2, 'Nordic Systems', 'Internal CRM Upgrade', '2026-08-15', 'Upgrade the existing CRM platform', '2026-05-04 13:10:00');

INSERT INTO subproject (
    subproject_id,
    project_id,
    name,
    deadline,
    description
) VALUES
(1, 1, 'Frontend Development', '2026-06-10', 'Build the public-facing frontend'),
(2, 1, 'Backend API', '2026-06-20', 'Create backend services and APIs'),
(3, 2, 'Data Migration', '2026-07-30', 'Move existing CRM data to the new system');

INSERT INTO competency (
    competency_id,
    name,
    daily_capacity_hours,
    description
) VALUES
(1, 'Frontend Developer', 6.50, 'Works with HTML, CSS, JavaScript and UI frameworks'),
(2, 'Backend Developer', 7.00, 'Works with databases, APIs and server-side logic'),
(3, 'Database Specialist', 5.50, 'Handles schema design, optimization and migration');

INSERT INTO task (
    task_id,
    subproject_id,
    competency_id,
    name,
    estimated_hours,
    deadline,
    description
) VALUES
(1, 1, 1, 'Create landing page', 16.00, '2026-05-20', 'Design and implement the main landing page'),
(2, 1, 1, 'Build navigation menu', 8.00, '2026-05-25', 'Implement responsive navigation'),
(3, 2, 2, 'Create authentication API', 20.00, '2026-06-01', 'Implement login and registration endpoints'),
(4, 3, 3, 'Prepare migration script', 24.00, '2026-07-10', 'Create scripts for CRM data migration'),
(5, 2, NULL, 'General project coordination', 10.00, '2026-06-05', 'Coordination task without assigned competency');
