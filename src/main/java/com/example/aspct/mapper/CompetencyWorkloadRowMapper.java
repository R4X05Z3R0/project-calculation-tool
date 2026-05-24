package com.example.aspct.mapper;


import com.example.aspct.model.CompetencyWorkload;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompetencyWorkloadRowMapper implements RowMapper<CompetencyWorkload> {

    @Override
    public CompetencyWorkload mapRow(ResultSet rs, int rowNum) throws SQLException {
        CompetencyWorkload workload = new CompetencyWorkload();

        workload.setCompetencyId(rs.getInt("competency_id"));
        workload.setCompetencyName(rs.getString("name"));
        workload.setallocatedHoursPerDay(rs.getDouble("daily_capacity_hours"));
        workload.setTotalEstimatedHours(rs.getDouble("total_hours"));

        return workload;
    }
}