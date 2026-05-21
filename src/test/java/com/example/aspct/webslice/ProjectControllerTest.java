package com.example.aspct.webslice;

import com.example.aspct.controller.ProjectController;
import com.example.aspct.model.Project;
import com.example.aspct.model.SubProject;
import com.example.aspct.service.ProjectService;
import com.example.aspct.service.SubProjectService;
import com.example.aspct.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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

    @Test
    public void testViewsListOfProjects () throws Exception{
        //Arrange
        Project p1 = new Project(
                1,
                "Stark Industries",
                "Arc Reactor",
                LocalDate.now(),
                "New Energy Source",
                LocalDateTime.now()
        );

        List<Project> fakeProjectList = List.of(p1);

        //Act
        Mockito.when(projectService.getAllProjects()).thenReturn(fakeProjectList);

        //Assert
        mockMvc.perform(get("/projects/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("views/view-projects"))
                .andExpect(model().attributeExists("projects"))
                .andExpect(model().attribute("projects",hasSize(1)));
    }
}