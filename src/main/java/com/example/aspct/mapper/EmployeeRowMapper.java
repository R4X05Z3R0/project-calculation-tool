package com.example.aspct.mapper;

import com.example.aspct.model.Employee
        ;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee
        > {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee Employee = new Employee();

        Employee.setEmployeeId(rs.getInt("employee_id"));
        Employee.setName(rs.getString("name"));
        Employee.setCompetencyId(rs.getInt("competency_id"));
        Employee.setWeeklyHours(rs.getDouble("weekly_hours"));

        return Employee;
    }
}