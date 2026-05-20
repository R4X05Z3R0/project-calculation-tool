package com.example.aspct.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ASPCTModelTest {
    @Test
    void testProjectConstructor(){
        LocalDate deadline = LocalDate.of(2026,5,19);
        LocalDateTime createdAt = LocalDateTime.of(2026, 5,19,12,0);

        Project project = new Project(1, "Alpha Solutions", "Calculation Tool", deadline, "Test project", createdAt);
        assertEquals(1, project.getProjectId());
        assertEquals("Alpha Solutions", project.getCompanyName());
        assertEquals("Calculation Tool", project.getProjectName());
        assertEquals(deadline, project.getDeadline());
        assertEquals("Test project", project.getDescription());
        assertEquals(createdAt, project.getCreatedAt());
    }
    @Test
    void testSubProjectsListStartsEmpty(){
        Project project = new Project();
        assertTrue(project.getSubProjects().isEmpty());
    }
    @Test
    void testSubProjectConstructor(){
        LocalDate deadline = LocalDate.of(2026,5,19);
        SubProject subProject = new SubProject(1,10,"Backend", deadline, "Develop backend");
        assertEquals(1, subProject.getSubProjectId());
        assertEquals(10, subProject.getProjectId());
        assertEquals("Backend", subProject.getName());
        assertEquals("Develop backend", subProject.getDescription());
        assertEquals(deadline, subProject.getDeadline());
    }
    @Test
    void testTaskListStartsEmpty(){
        SubProject subProject = new SubProject();
        assertTrue(subProject.getTasks().isEmpty());
    }

    @Test
    void testTaskConstructor(){
        LocalDate deadline = LocalDate.of(2026,5,19);
        Task task = new Task(1,5,"Database Design", 12.5,deadline,"Design the Database");

        assertEquals(1, task.getTaskId());
        assertEquals(5, task.getSubProjectId());
        assertEquals("Database Design", task.getName());
        assertEquals(12.5, task.getEstimatedHours());
        assertEquals("Design the Database", task.getDescription());
        assertEquals(deadline, task.getDeadline());
    }
}
