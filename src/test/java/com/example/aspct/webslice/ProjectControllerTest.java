package com.example.aspct.webslice;

import com.example.aspct.controller.ProjectController;
import com.example.aspct.model.Project;
import com.example.aspct.model.SubProject;
import com.example.aspct.service.ProjectService;
import com.example.aspct.service.SubProjectService;
import com.example.aspct.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private SubProjectService subProjectService;

    @MockitoBean
    private TaskService taskService;

    private Project testProject; //Test Project
    private List<Project> testProjectList; //List of test projects

    //Starts before every individual test
    @BeforeEach
    public void setUp(){
        testProject = new Project(
                1,
                "Stark Industries",
                "Arc Reactor",
                LocalDate.now(),
                "New Energy Source",
                LocalDateTime.now()
        );

        testProjectList = List.of(testProject);
    }

    @Test
    public void testViewsListOfProjects() throws Exception{

        //Act
        Mockito.when(projectService.getAllProjects()).thenReturn(testProjectList);

        //Assert
        mockMvc.perform(get("/projects/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("views/view-projects"))
                .andExpect(model().attributeExists("projects"))
                .andExpect(model().attribute("projects",hasSize(1)));
    }

    @Test
    public void testProjectEditForm() throws Exception{
        //Act
        Mockito.when(projectService.getProject(1)).thenReturn(testProject);

        mockMvc.perform(get("/projects/1/edit-form"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("edit/edit-project"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attribute("project", testProject));
    }

    @Test
    public void testProjectUpdates_AndRedirectsToDashboard() throws Exception {
        //Arrange
        int mockID = 1;
        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);

        mockMvc.perform(post("/projects/"+ mockID +"/update")
                .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                        .param("projectName", "Arc Reactor - Updated")
                        .param("companyName", "Alpha Industries")
                        .param("description", "New Arc Reactor Design")
                        .param("deadline", "2026-05-22"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/"));

        verify(projectService).updateProject(captor.capture());

        Project gotcha = captor.getValue();
        assertEquals("Arc Reactor - Updated",gotcha.getProjectName());
        assertEquals("New Arc Reactor Design", gotcha.getDescription());
        assertEquals("Alpha Industries", gotcha.getCompanyName());
        assertEquals("2026-05-22", gotcha.getDeadline().toString());
    }
}