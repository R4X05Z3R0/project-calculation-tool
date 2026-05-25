package com.example.aspct.mapper;


import com.example.aspct.model.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProjectRowMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();

        project.setProjectId(rs.getInt("project_id"));
        project.setCompanyName(rs.getString("company_name"));
        project.setProjectName(rs.getString("project_name"));
        Date deadline = rs.getDate("deadline");
        if (deadline != null) {
            project.setDeadline(deadline.toLocalDate());
        }
        project.setDescription(rs.getString("description"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            project.setCreatedAt(createdAt.toLocalDateTime());
        }

        return project;
    }
}