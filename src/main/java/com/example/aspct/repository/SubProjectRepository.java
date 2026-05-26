package com.example.aspct.repository;


import com.example.aspct.mapper.SubProjectRowMapper;
import com.example.aspct.model.SubProject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SubProjectRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SubProjectRowMapper subProjectRowMapper;

    public SubProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.subProjectRowMapper = new SubProjectRowMapper();
    }

    public List<SubProject> findByProjectId(int projectId) {
        String sql = """
                SELECT subproject_id, project_id, name, deadline, description
                FROM subproject
                WHERE project_id = ?
                ORDER BY subproject_id
                """;
        return jdbcTemplate.query(sql, subProjectRowMapper, projectId);
    }

    public SubProject findById(int subProjectId) {
        String sql = """
                SELECT subproject_id, project_id, name, deadline, description
                FROM subproject
                WHERE subproject_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, subProjectRowMapper, subProjectId);
    }

    public SubProject save(SubProject subProject) {
        String sql = """
                INSERT INTO subproject (project_id, name, deadline, description)
                VALUES (?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, subProject.getProjectId());
            ps.setString(2, subProject.getName());
            ps.setDate(3, subProject.getDeadline() != null ? Date.valueOf(subProject.getDeadline()) : null);
            ps.setString(4, subProject.getDescription());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            subProject.setSubProjectId(key.intValue());
        }

        return subProject;
    }

    public void update(SubProject subProject) {
        String sql = """
                UPDATE subproject
                SET name = ?, deadline = ?, description = ?
                WHERE subproject_id = ?
                """;
        jdbcTemplate.update(sql,
                subProject.getName(),
                subProject.getDeadline() != null ? Date.valueOf(subProject.getDeadline()) : null,
                subProject.getDescription(),
                subProject.getSubProjectId());
    }

    public void deleteById(int subProjectId) {
        String sql = "DELETE FROM subproject WHERE subproject_id = ?";
        jdbcTemplate.update(sql, subProjectId);
    }
}