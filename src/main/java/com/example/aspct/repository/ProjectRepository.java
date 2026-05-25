package com.example.aspct.repository;


import com.example.aspct.mapper.ProjectRowMapper;
import com.example.aspct.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProjectRowMapper projectRowMapper;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.projectRowMapper = new ProjectRowMapper();
    }

    public List<Project> findAllActive(){
        String sql = """
                SELECT project_id, company_name, project_name, deadline, description, created_at, is_archived
                FROM project
                WHERE is_archived = FALSE
                ORDER BY created_at DESC
                """;

        return jdbcTemplate.query(sql, projectRowMapper);
    }

    public List<Project> findAllArchived(){
        String sql = """
                SELECT project_id, company_name, project_name, deadline, description, created_at, is_archived
                FROM project
                WHERE is_archived = TRUE
                ORDER BY created_at DESC
                """;

        return jdbcTemplate.query(sql, projectRowMapper);
    }

    public List<Project> findAll() {
        String sql = """
                SELECT project_id, company_name, project_name, deadline, description, created_at, is_archived
                FROM project
                ORDER BY created_at DESC
                """;
        return jdbcTemplate.query(sql, projectRowMapper);
    }

    public Project findById(int projectId) {
        String sql = """
                SELECT project_id, company_name, project_name, deadline, description, created_at, is_archived
                FROM project
                WHERE project_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, projectRowMapper, projectId);
    }

    public Project save(Project project) {
        String sql = """
                INSERT INTO project (company_name, project_name, deadline, description)
                VALUES (?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, project.getCompanyName());
            ps.setString(2, project.getProjectName());
            ps.setDate(3, Date.valueOf(project.getDeadline()));
            ps.setString(4, project.getDescription());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            project.setProjectId(key.intValue());
        }
        return project;
    }

    public void update(Project project) {
        String sql = """
                UPDATE project
                SET company_name = ?, project_name = ?, deadline = ?, description = ?
                WHERE project_id = ?
                """;
        jdbcTemplate.update(sql,
                project.getCompanyName(),
                project.getProjectName(),
                Date.valueOf(project.getDeadline()),
                project.getDescription(),
                project.getProjectId());
    }

    public void archiveById(int projectId){
        String sql = "UPDATE project SET is_archived = TRUE WHERE project_id = ?";
        jdbcTemplate.update(sql, projectId);
    }

    public void restoreById(int projectId){
        String sql = "UPDATE project SET is_archived = FALSE WHERE project_id = ?";
        jdbcTemplate.update(sql, projectId);
    }

    public void deleteById(int projectId) {
        String sql = "DELETE FROM project WHERE project_id = ?";
        jdbcTemplate.update(sql, projectId);
    }
}