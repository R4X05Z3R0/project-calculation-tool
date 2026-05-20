package com.example.aspct.mappertest;

import com.example.aspct.mapper.ProjectRowMapper;
import com.example.aspct.mapper.SubProjectRowMapper;
import com.example.aspct.mapper.TaskRowMapper;
import com.example.aspct.model.Project;
import com.example.aspct.model.SubProject;
import com.example.aspct.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ASPCTMapperTest {
    @Test
    void testProjectRowMapper()throws Exception{
        ResultSet rs = mock(ResultSet.class);

        LocalDate deadline = LocalDate.of(2026, 5, 17);
        LocalDateTime createdAt = LocalDateTime.of(2026,5,17,12,0);

        when(rs.getInt("project_id")).thenReturn(1);
        when(rs.getString("company_name")).thenReturn("Alpha Solutions");
        when(rs.getString("project_name")).thenReturn("Calculation tool");
        when(rs.getDate("deadline")).thenReturn(Date.valueOf(deadline));
        when(rs.getString("description")).thenReturn("Test project");

        Timestamp timestamp = Timestamp.valueOf(createdAt);
        when(rs.getTimestamp("created_at")).thenReturn(timestamp);


        ProjectRowMapper mapper = new ProjectRowMapper();
        Project project = mapper.mapRow(rs, 1);

        assertEquals(1, project.getProjectId());
        assertEquals("Alpha Solutions", project.getCompanyName());
        assertEquals("Calculation tool", project.getProjectName());
        assertEquals(deadline, project.getDeadline());
        assertEquals("Test project", project.getDescription());
        assertEquals(createdAt, project.getCreatedAt());
    }
    @Test
    void testProjectMapRowWithNullTimeStamp() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getDate("deadline")).thenReturn(Date.valueOf(LocalDate.now()));
        when(rs.getTimestamp("created_at")).thenReturn(null);
        ProjectRowMapper mapper = new ProjectRowMapper();
        Project project = mapper.mapRow(rs, 1);
        assertNull(project.getCreatedAt());


    }
    @Test
    void testSubProjectMapRow() throws Exception{
        ResultSet rs = mock(ResultSet.class);

        LocalDate deadline = LocalDate.of(2026, 5, 17);
        when(rs.getInt("subproject_id")).thenReturn(1);
        when(rs.getInt("project_id")).thenReturn(10);
        when(rs.getString("name")).thenReturn("Backend");
        when(rs.getString("description")).thenReturn("Develop backend");
        when(rs.getDate("deadline")).thenReturn(Date.valueOf(deadline));

        SubProjectRowMapper mapper = new SubProjectRowMapper();
        SubProject subProject = mapper.mapRow(rs, 1);

        assertEquals(1, subProject.getSubProjectId());
        assertEquals(10, subProject.getProjectId());
        assertEquals("Backend", subProject.getName());
        assertEquals("Develop backend", subProject.getDescription());
        assertEquals(deadline, subProject.getDeadline());
    }
    @Test
    void testSubProjectMapRowWithNullDeadline() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getDate("deadline")).thenReturn(null);
        SubProjectRowMapper mapper = new SubProjectRowMapper();
        SubProject subProject = mapper.mapRow(rs, 1);
        assertNull(subProject.getDeadline());

    }
    @Test
    void testTaskMapRow() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        LocalDate deadline = LocalDate.of(2026, 6, 19);

        when(rs.getInt("task_id")).thenReturn(1);
        when(rs.getInt("subproject_id")).thenReturn(5);
        when(rs.getString("name")).thenReturn("Database Design");
        when(rs.getDouble("estimated_hours")).thenReturn(12.5);
        when(rs.getString("description")).thenReturn("Design the Database");
        when(rs.getDate("deadline")).thenReturn(Date.valueOf(deadline));

        TaskRowMapper mapper = new TaskRowMapper();
        Task task = mapper.mapRow(rs, 1);

        assertEquals(1, task.getTaskId());
        assertEquals(5, task.getSubProjectId());
        assertEquals("Database Design", task.getName());
        assertEquals(12.5, task.getEstimatedHours());
        assertEquals("Design the Database", task.getDescription());
        assertEquals(deadline, task.getDeadline());

    }
    @Test
    void testTaskMapRowWithNullDeadline() throws Exception{
        ResultSet rs = mock(ResultSet.class);
        when(rs.getDate("deadline")).thenReturn(null);

        TaskRowMapper mapper = new TaskRowMapper();
        Task task = mapper.mapRow(rs, 1);
        assertNull(task.getDeadline());

    }
}
