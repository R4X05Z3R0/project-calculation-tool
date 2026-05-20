package com.example.aspct.service;

import com.example.aspct.model.Task;
import com.example.aspct.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task(1, 1, "Design", 10, LocalDate.of(2026, 5, 20), "frontend design");
        task2 = new Task(2, 1, "build", 20, LocalDate.of(2026, 5, 25), "backend implementation");
    }

    @Test
    void getTasksBySubProjectId_returnsList() {
        when(taskRepository.findBySubProjectId(1)).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.getTasksBySubProjectId(1);

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findBySubProjectId(1);
    }

    @Test
    void getTotalHoursForSubProject_sumsCorrectly() {
        when(taskRepository.findBySubProjectId(1)).thenReturn(List.of(task1, task2));

        double total = taskService.getTotalHoursForSubProject(1);

        assertEquals(30, total);
    }

    @Test
    void getTotalEstimatedHoursForSubProject_emptyList_returnsZero() {
        when(taskRepository.findBySubProjectId(99)).thenReturn(List.of());

        double total = taskService.getTotalHoursForSubProject(99);

        assertEquals(0, total);
    }

}





