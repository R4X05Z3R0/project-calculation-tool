package com.example.aspct.mapper;

import com.example.aspct.model.Employee
        ;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();

        employee.setEmployeeId(rs.getInt("employee_id"));
        employee.setName(rs.getString("name"));
        employee.setCompetencyId(rs.getInt("competency_id"));
        employee.setDailyHours(rs.getDouble("daily_hours"));

        return employee;
    }
}