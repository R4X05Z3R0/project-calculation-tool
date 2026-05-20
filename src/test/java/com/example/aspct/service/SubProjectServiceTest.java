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
public class SubProjectServiceTest {

    @Mock
    private SubProjectRepository subProjectRepository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private SubProjectService subProjectService;

    private SubProject subProject1;
    private SubProject subProject2;

    @BeforeEach
    void setUp() {
        subProject1 = new SubProject(1, 1, "Frontend", LocalDate.of(2026, 5, 5), "Frontend implementation");
        subProject2 = new SubProject(2, 1, "Backend", LocalDate.of(2026, 6, 6), "Backend implenemtation");

    }

    @Test
    void getSubProjectByProjectId_returnList() {

        when(subProjectRepository.findByProjectId(1)).thenReturn(List.of(subProject1, subProject2));

        List<SubProject> result = subProjectService.getSubProjectsByProjectId(1);

        assertEquals(2, result.size());

        verify(subProjectRepository, times(1)).findByProjectId(1);
    }

    @Test
    void getSubProjectsByProjectId_emptyList_returnsEmptyList() {
        when(subProjectRepository.findByProjectId(99)).thenReturn(List.of());

        List<SubProject> result = subProjectService.getSubProjectsByProjectId(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void getSubprojectsWithTasks_returnsSubProjectsWithTasks() {

        Task task1 = new Task();
        task1.setTaskId(1);
        task1.setName("UI design");

        Task task2 = new Task();
        task2.setTaskId(2);
        task2.setName("API setup");

        when(subProjectRepository.findByProjectId(1)).thenReturn(List.of(subProject1, subProject2));

        when(taskService.getTasksBySubProjectId(1)).thenReturn(List.of(task1));

        when(taskService.getTasksBySubProjectId(2)).thenReturn(List.of(task2));

        List<SubProject> result = subProjectService.getSubProjectsWithTasks(1);

        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getTasks().size());

        verify(subProjectRepository, times(1)).findByProjectId(1);

        verify(taskService, times(1)).getTasksBySubProjectId(1);

        verify(taskService, times(1)).getTasksBySubProjectId(2);
    }

    @Test
    void createSubProject_callsRepositorySave() {

        when(subProjectRepository.save(subProject1)).thenReturn(subProject1);

        SubProject result = subProjectService.createSubProject(subProject1);

        assertEquals("Frontend", result.getName());

        verify(subProjectRepository, times(1)).save(subProject1);
    }

    @Test
    void updateSubProject_callsRepositoryUpdate() {

        subProject1.setName("Updated frontend");

        subProjectService.updateSubProject(subProject1);

        verify(subProjectRepository, times(1)).update(subProject1);

    }

    @Test
    void updateSubProject_doesNotCallSave() {

        subProjectService.updateSubProject(subProject1);

        verify(subProjectRepository, never()).save(any());

        verify(subProjectRepository, times(1)).update(subProject1);

    }

    @Test
    void deleteSubProject_callRepositoryDeleteById() {

        subProjectService.deleteSubProject(1);

        verify(subProjectRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteSubProject_doesNotInteractWithTaskService() {

        subProjectService.deleteSubProject(1);

        verifyNoInteractions(taskService);
    }

















}
