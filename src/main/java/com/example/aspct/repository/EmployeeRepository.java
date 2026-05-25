package com.example.aspct.repository;


import com.example.aspct.mapper.EmployeeRowMapper;
import com.example.aspct.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
// Database for Medarbejdere. Indeholder også Medarbejder-på-projekt listen i databasen, en "Workforce".
// TODO Medarbejdere er lige nu hardcodede fra SQL i databasen og vi mangler at implementere en tilføjelses og fjernelses-funktion af nye Medarbejdere.
@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeRowMapper employeeRowMapper;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeRowMapper = new EmployeeRowMapper();
    }

    public List<Employee> findAll() {
        String sql = """
                SELECT employee_id, name, competency_id, daily_hours
                FROM employee
                ORDER BY employee_id
                """;

        return jdbcTemplate.query(sql, employeeRowMapper);
    }
    public List<Employee> findByProjectId(int projectId) {
        String sql = """
            SELECT e.employee_id, e.name, e.competency_id, e.daily_hours
            FROM employee e
            JOIN project_employee pe
                ON e.employee_id = pe.employee_id
            WHERE pe.project_id = ?
            ORDER BY e.employee_id
            """;

        return jdbcTemplate.query(sql, employeeRowMapper, projectId);
    }
// Tilføjer en medarbejder til  et "connector table" af Project ID og  Employee ID så man kan se hvem der er tilføjet.

    public void addEmployeeToProject(int projectId, int employeeId) {
        String sql = """
            INSERT INTO project_employee (project_id, employee_id)
            VALUES (?, ?)
            """;

        jdbcTemplate.update(sql, projectId, employeeId);
    }
// fjerner medarbejderens tilknytning til et projekt.

    public void removeEmployeeFromProject(int projectId, int employeeId) {
        String sql = """
            DELETE FROM project_employee
            WHERE project_id = ?
              AND employee_id = ?
            """;

        jdbcTemplate.update(sql, projectId, employeeId);
    }
// Her finder vi de medarbejdere/arbejdsstyrke
    public List<Employee> findEmployeesByProjectId(int projectId) {
        String sql = """
            SELECT e.employee_id, e.name, e.competency_id, e.daily_hours
            FROM employee e
            JOIN project_employee pe
                ON e.employee_id = pe.employee_id
            WHERE pe.project_id = ?
            ORDER BY e.name
            """;

        return jdbcTemplate.query(sql, employeeRowMapper, projectId);
    }
// En liste over folk der ikke er tilknyttet projektet. Bruges til at vise hvem der KAN tilføjes til et projekt.
    public List<Employee> findEmployeesNotOnProject(int projectId) {
        String sql = """
            SELECT e.employee_id, e.name, e.competency_id, e.daily_hours
            FROM employee e
            WHERE e.employee_id NOT IN (
                SELECT pe.employee_id
                FROM project_employee pe
                WHERE pe.project_id = ?
            )
            ORDER BY e.name
            """;

        return jdbcTemplate.query(sql, employeeRowMapper, projectId);
    }
}