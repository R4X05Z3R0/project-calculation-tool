package com.example.aspct.repository;

import com.example.aspct.mapper.CompetencyWorkloadRowMapper;
import com.example.aspct.model.CompetencyWorkload;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class PlannerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CompetencyWorkloadRowMapper competencyWorkloadRowMapper;

    public PlannerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.competencyWorkloadRowMapper = new CompetencyWorkloadRowMapper();
    }

    public List<CompetencyWorkload> findHoursPerCompetencyByProject(int projectId) {
        String sql = """
                SELECT c.competency_id, c.name, c.daily_capacity_hours,
                       SUM(t.estimated_hours) AS total_hours
                FROM task t
                JOIN subproject sp ON t.subproject_id = sp.subproject_id
                JOIN competency c ON t.competency_id = c.competency_id
                WHERE sp.project_id = ?
                GROUP BY c.competency_id, c.name, c.daily_capacity_hours
                ORDER BY total_hours DESC
                """;
        return jdbcTemplate.query(sql, competencyWorkloadRowMapper, projectId);
    }

    public double findUnassignedHoursByProject(int projectId) {
        String sql = """
                SELECT COALESCE(SUM(t.estimated_hours), 0) AS total_hours
                FROM task t
                JOIN subproject sp ON t.subproject_id = sp.subproject_id
                WHERE sp.project_id = ? AND t.competency_id IS NULL
                """;
        Double result = jdbcTemplate.queryForObject(sql, Double.class, projectId);
        return result != null ? result : 0.0;
    }
}