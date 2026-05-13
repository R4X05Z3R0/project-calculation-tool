package com.example.aspct.mapper;

import com.example.aspct.model.SubProject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubProjectRowMapper implements RowMapper<SubProject> {

    @Override
    public SubProject mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubProject subProject = new SubProject();

        subProject.setSubProjectId(rs.getInt("subproject_id"));
        subProject.setProjectId(rs.getInt("project_id"));
        subProject.setName(rs.getString("name"));
        subProject.setDescription(rs.getString("description"));

        Date deadline = rs.getDate("deadline");
        if (deadline != null) {
            subProject.setDeadline(deadline.toLocalDate());
        }

        return subProject;
    }
}