package com.example.aspct.mapper;

import com.example.aspct.model.Task;
import org.springframework.jdbc.core.RowMapper;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();

        task.setTaskId(rs.getInt("task_id"));
        task.setSubProjectId(rs.getInt("subproject_id"));
        task.setName(rs.getString("name"));
        task.setEstimatedHours(rs.getDouble("estimated_hours"));
        task.setDescription(rs.getString("description"));
        task.setCompetencyId(rs.getInt("competency_id"));

        Date deadline = rs.getDate("deadline");
        if (deadline != null) {
            task.setDeadline(deadline.toLocalDate());
        }

        return task;
    }
}