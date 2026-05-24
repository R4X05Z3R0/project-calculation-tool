package com.example.aspct.repository;


import com.example.aspct.mapper.EmployeeRowMapper;
import com.example.aspct.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}