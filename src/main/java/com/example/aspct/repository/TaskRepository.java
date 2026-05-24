package com.example.aspct.repository;

import com.example.aspct.mapper.TaskRowMapper;
import com.example.aspct.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TaskRowMapper taskRowMapper;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskRowMapper = new TaskRowMapper();
    }

    public List<Task> findBySubProjectId(int subProjectId) {
        String sql = """
                SELECT task_id, subproject_id, competency_id, name, estimated_hours, deadline, description
                FROM task
                WHERE subproject_id = ?
                ORDER BY task_id
                """;
        return jdbcTemplate.query(sql, taskRowMapper, subProjectId);
    }

    public Task findById(int taskId) {
        String sql = """
                SELECT task_id, subproject_id, competency_id, name, estimated_hours, deadline, description
                FROM task
                WHERE task_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, taskRowMapper, taskId);
    }

    public Task save(Task task) {
        String sql = """
                INSERT INTO task (subproject_id, competency_id, name, estimated_hours, deadline, description)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, task.getSubProjectId());
            ps.setInt(2, task.getCompetencyId());
            ps.setString(3, task.getName());
            ps.setDouble(4, task.getEstimatedHours());
            ps.setDate(5, task.getDeadline() != null ? Date.valueOf(task.getDeadline()) : null);
            ps.setString(6, task.getDescription());

            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            task.setTaskId(key.intValue());
        }
        return task;
    }

    public void update(Task task) {
        String sql = """
                UPDATE task
                SET name = ?, estimated_hours = ?, deadline = ?, description = ?, competency_id = ?
                WHERE task_id = ?
                """;
        jdbcTemplate.update(sql,
                task.getName(),
                task.getEstimatedHours(),
                task.getDeadline() != null ? Date.valueOf(task.getDeadline()) : null,
                task.getDescription(),
                task.getCompetencyId(),
                task.getTaskId() );
    }

    public void deleteById(int taskId) {
        String sql = "DELETE FROM task WHERE task_id = ?";
        jdbcTemplate.update(sql, taskId);
    }
}