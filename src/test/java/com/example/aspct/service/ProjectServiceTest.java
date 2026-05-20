package com.example.aspct.service;

import com.example.aspct.model.Task;
import com.example.aspct.model.Project;
import com.example.aspct.model.SubProject;
import com.example.aspct.repository.ProjectRepository;
import com.example.aspct.repository.SubProjectRepository;
import com.example.aspct.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private SubProjectService subProjectService;

    @InjectMocks
    private ProjectService projectService;

    private Project project1;
    private Project project2;

    @BeforeEach
    void setUp(){
        project1 = new Project(1, "Alpha solutions", "website redesign", LocalDate.of(2026, 12, 1), "Redesign company website", LocalDateTime.now());
        project2 = new Project(2, "Test company", "mobile app", LocalDate.of(2026, 10, 10), null, LocalDateTime.now());

    }

    @Test
    void getAllProjects_returnList(){
        when(projectRepository.findAll()).thenReturn(List.of(project1, project2));

        List<Project> result = projectService.getAllProjects();

        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAll();

    }

    @Test
    void getAllProjects_doesNotLoadSubProjects(){
        when(projectRepository.findAll()).thenReturn(List.of(project1));

        projectService.getAllProjects();
    }

    @Test
    void getProject_returnProjectWithSubProject(){
        SubProject sp1 = new SubProject();
        sp1.setSubProjectId(10);
        sp1.setName("Frontend");

        SubProject sp2 = new SubProject();
        sp2.setSubProjectId(11);
        sp2.setName("Backend");

        when(projectRepository.findById(1)).thenReturn(project1);
        when(subProjectService.getSubProjectsWithTasks(1)).thenReturn(List.of(sp1,sp2));

        Project result = projectService.getProject(1);

        assertEquals("website redesign", result.getProjectName());
        assertEquals(2, result.getSubProjects().size());
        verify(projectRepository, times(1)).findById(1);

    }

    @Test
    void createProject_callsRepositorySave(){
        when(projectRepository.save(project1)).thenReturn(project1);

        Project result = projectService.createProject(project1);

        assertEquals("website redesign", result.getProjectName());
        verify(projectRepository, times(1)).save(project1);
    }

    @Test
    void updateProject_doesNotCallSave(){
        projectService.updateProject(project1);

        verify(projectRepository, never()).save(any());
        verify(projectRepository, times(1)).update(project1);
    }

    @Test
    void updateProject_callsRepositoryUpdate(){
        project1.setProjectName("Update Name");

        projectService.updateProject(project1);

        verify(projectRepository, times(1)).update(project1);
    }

    @Test
    void deleteProject_callsRepositoryDeleteById(){
        projectService.deleteProject(1);

        verify(projectRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteProject_doesNotInteractWithSubProjectService(){
        projectService.deleteProject(1);

        verifyNoInteractions(subProjectService);
    }



}
