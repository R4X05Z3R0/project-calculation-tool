package com.example.aspct.webslice;

import com.example.aspct.controller.TaskController;
import com.example.aspct.model.Task;
import com.example.aspct.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private Task testTask;

    @BeforeEach
    public void setUp() {
        // Initialize sample Task data
        testTask = new Task();
        testTask.setTaskId(200);
        testTask.setSubProjectId(45); // Linked to parent sub-project ID 45
        testTask.setName("Calibrate Thermal Shields");
    }

    @Test
    public void testEditTaskForm_ReturnsCorrectViewAndModel() throws Exception {
        int taskId = 200;

        // Arrange Mock
        Mockito.when(taskService.getTask(taskId)).thenReturn(testTask);

        // Act & Assert
        mockMvc.perform(get("/tasks/" + taskId + "/edit"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("edit/edit-task"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attribute("task", testTask));
    }

    @Test
    public void testShowCreateForm_PrepopulatesSubProjectId() throws Exception {
        int subId = 24;

        mockMvc.perform(get("/tasks/create-form").param("subProjectId", String.valueOf(subId)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("create/create-task"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attribute("task", hasProperty("subProjectId", is(subId))));
    }

    @Test
    public void testCreateTask_BindsFormAndRedirectsToTaskDashboard() throws Exception {
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);

        mockMvc.perform(post("/tasks/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("subProjectId", "24")
                        .param("name", "Assemble Propulsion Rails")
                        .param("estimatedHours", "4.5")
                        .param("deadline", "2026-11-30")
                        .param("description", "Calibrate magnetic stabilization limits"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subprojects/24/tasks"));

        verify(taskService).createTask(captor.capture());

        Task captured = captor.getValue();
        assertEquals(24, captured.getSubProjectId());
        assertEquals("Assemble Propulsion Rails", captured.getName());
        assertEquals(4.5, captured.getEstimatedHours());
        assertEquals("2026-11-30", captured.getDeadline().toString());
        assertEquals("Calibrate magnetic stabilization limits", captured.getDescription());
    }

    @Test
    public void testUpdateTask_CapturesModelAndRedirectsToSubProjectTasks() throws Exception {
        int taskId = 200;
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);

        // Act & Assert
        mockMvc.perform(post("/tasks/" + taskId + "/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        // Emulate fields coming from your Thymeleaf edit-task form template
                        .param("subProjectId", "45")
                        .param("name", "Calibrate Thermal Shields - Verified"))
                .andExpect(status().is3xxRedirection())
                // Matches your exact return string structure (including that trailing space character)
                .andExpect(redirectedUrl("/subprojects/45/tasks"));

        // Verify that the service layer received the mapped task values
        verify(taskService).updateTask(captor.capture());

        Task capturedTask = captor.getValue();
        assertEquals(200, capturedTask.getTaskId()); // Bound via URL Path variable
        assertEquals(45, capturedTask.getSubProjectId()); // Bound via form parameter
        assertEquals("Calibrate Thermal Shields - Verified", capturedTask.getName());
    }

    @Test
    public void testDeleteTask_RemovesRecordAndRedirectsToParentView() throws Exception {
        int targetTaskId = 99;
        int parentSubId = 24;

        mockMvc.perform(post("/tasks/" + targetTaskId + "/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("subProjectId", String.valueOf(parentSubId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subprojects/24/tasks"));

        verify(taskService).deleteTask(targetTaskId);
    }
}