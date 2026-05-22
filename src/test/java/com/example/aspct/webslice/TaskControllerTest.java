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
}