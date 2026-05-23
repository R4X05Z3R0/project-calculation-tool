package com.example.aspct.webslice;

import com.example.aspct.controller.SubProjectController;
import com.example.aspct.model.SubProject;
import com.example.aspct.model.Task;
import com.example.aspct.service.SubProjectService;
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

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubProjectController.class)
public class SubProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubProjectService subProjectService;

    @MockitoBean
    private TaskService taskService;

    private SubProject testSubProject;
    private Task testTask;

    @BeforeEach
    public void setUp() {
        // Initialize sample SubProject data
        testSubProject = new SubProject();
        testSubProject.setSubProjectId(10);
        testSubProject.setProjectId(5); // Linked to parent project ID 5
        testSubProject.setName("Arc Reactor Propulsion");
        testSubProject.setDescription("Energy Redirection");
        testSubProject.setDeadline(LocalDate.now());

        // Initialize sample Task data
        testTask = new Task();
        testTask.setTaskId(101);
        testTask.setSubProjectId(10);
        testTask.setName("Core Shell Assembly");
    }

    @Test
    public void testShowCreateForm_PrepopulatesParentProjectId() throws Exception {
        int parentId = 5;

        mockMvc.perform(get("/subprojects/create-form")
                        .param("projectId", String.valueOf(parentId)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("create/create-subproject"))
                .andExpect(model().attributeExists("subProject"))
                // Asserts the instantiated model object inside the model maps the correct ID
                .andExpect(model().attribute("subProject", hasProperty("projectId", is(parentId))));
    }

    @Test
    public void testCreateSubProject_SavesFormFieldsAndRedirects() throws Exception {
        ArgumentCaptor<SubProject> captor = ArgumentCaptor.forClass(SubProject.class);

        mockMvc.perform(post("/subprojects/create")
                        .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                        .param("projectId", "5")
                        .param("name", "Vibranium Shield Calibration")
                        .param("deadline", "2026-09-18")
                        .param("description", "Testing physics defying engine"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/5/subprojects"));

        verify(subProjectService).createSubProject(captor.capture());

        SubProject captured = captor.getValue();
        assertEquals(5, captured.getProjectId());
        assertEquals("Vibranium Shield Calibration", captured.getName());
        assertEquals("2026-09-18", captured.getDeadline().toString());
        assertEquals("Testing physics defying engine", captured.getDescription());
    }
    @Test
    public void testGetTasks_ReturnsViewWithTasksAndHours() throws Exception {
        int subProjectId = 10;
        List<Task> taskList = List.of(testTask);

        // Arrange Mocks
        Mockito.when(subProjectService.getSubProject(subProjectId)).thenReturn(testSubProject);
        Mockito.when(taskService.getTasksBySubProjectId(subProjectId)).thenReturn(taskList);
        Mockito.when(taskService.getTotalHoursForSubProject(subProjectId)).thenReturn(42.0);

        // Act & Assert
        mockMvc.perform(get("/subprojects/" + subProjectId + "/tasks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("views/view-tasks"))
                .andExpect(model().attributeExists("tasks", "subProject", "totalHours"))
                .andExpect(model().attribute("tasks", hasSize(1)))
                .andExpect(model().attribute("subProject", testSubProject))
                .andExpect(model().attribute("totalHours", 42));
    }

    @Test
    public void testEditSubProjectForm_ReturnsCorrectViewAndModel() throws Exception {
        int subProjectId = 10;

        // Arrange Mock
        Mockito.when(subProjectService.getSubProject(subProjectId)).thenReturn(testSubProject);

        // Act & Assert
        mockMvc.perform(get("/subprojects/" + subProjectId + "/edit-form"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("edit/edit-subprojects"))
                .andExpect(model().attributeExists("subProject"))
                .andExpect(model().attribute("subProject", testSubProject));
    }

    @Test
    public void testUpdateSubProject_CapturesModelAndRedirectsToParentProject() throws Exception {
        int subProjectId = 10;
        ArgumentCaptor<SubProject> captor = ArgumentCaptor.forClass(SubProject.class);

        // Act & Assert
        mockMvc.perform(post("/subprojects/" + subProjectId + "/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        // Emulate data coming from the thymeleaf template form fields (via @ModelAttribute)
                        .param("projectId", "5")
                        .param("name", "Arc Reactor Propulsion - Optimized")
                        .param("deadline", "2026-05-22")
                        .param("description", "New Description"))
                .andExpect(status().is3xxRedirection())
                // Verify it correctly redirects to the dynamic parent project ID path matching your return statement
                .andExpect(redirectedUrl("/projects/5/subprojects"));

        // Verify that the service layer received the mapped subproject values
        verify(subProjectService).updateSubProject(captor.capture());

        SubProject capturedSubProject = captor.getValue();
        assertEquals(10, capturedSubProject.getSubProjectId()); // Bound via URL Path variable
        assertEquals(5, capturedSubProject.getProjectId());
        assertEquals("2026-05-22", capturedSubProject.getDeadline().toString());
        assertEquals("New Description", capturedSubProject.getDescription());
        assertEquals("Arc Reactor Propulsion - Optimized", capturedSubProject.getName());
    }


    @Test
    public void testDeleteSubProject_ExecutesServiceAndRedirectsToParentDashboard() throws Exception {
        int targetSubId = 10;
        int parentProjectId = 5;

        mockMvc.perform(post("/subprojects/" + targetSubId + "/delete")
                        .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                        .param("projectId", String.valueOf(parentProjectId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/5/subprojects"));

        verify(subProjectService).deleteSubProject(targetSubId);
    }
}