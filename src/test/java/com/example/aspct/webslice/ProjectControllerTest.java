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
    public void testShowCreateForm_ReturnsViewWithNewProjectModel() throws Exception {
        mockMvc.perform(get("/projects/create-form"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("create/create-project"))
                .andExpect(model().attributeExists("project"));
    }

    @Test
    public void testCreateProject_BindsFieldsCorrectlyAndRedirects() throws Exception {
        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);

        mockMvc.perform(post("/projects/create")
                        .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                        .param("companyName", "Pym Technologies")
                        .param("projectName", "Ant-Man Suit Scaling")
                        .param("deadline", "2026-08-14")
                        .param("description", "Atomic mass and scale displacement trials"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/"));

        verify(projectService).createProject(captor.capture());

        Project validatedProject = captor.getValue();
        assertEquals("Pym Technologies", validatedProject.getCompanyName());
        assertEquals("Ant-Man Suit Scaling", validatedProject.getProjectName());
        assertEquals("2026-08-14", validatedProject.getDeadline().toString());
        assertEquals("Atomic mass and scale displacement trials", validatedProject.getDescription());
    }

    @Test
    public void testViewsListOfProjects() throws Exception{

        //Act
        Mockito.when(projectService.getAllActiveProjects()).thenReturn(testProjectList);

        //Assert
        mockMvc.perform(get("/projects/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("views/view-projects"))
                .andExpect(model().attributeExists("projects"))
                .andExpect(model().attribute("projects",hasSize(1)));

        verify(projectService).getAllActiveProjects();
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

        verify(projectService).getProject(1);
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

    @Test
    public void testSubProjectView_WithTasks() throws Exception{
        //Arrange
        int fakeID = 7;
        SubProject testSub = new SubProject();
        List<SubProject> testSubList = List.of(testSub);

        testProject.setProjectId(fakeID);
        //False data to retrn
        Mockito.when(projectService.getProject(7)).thenReturn(testProject);
        Mockito.when(subProjectService.getTotalHoursForProject(7)).thenReturn(67.0);
        Mockito.when(subProjectService.getSubProjectsWithTasks(7)).thenReturn(testSubList);

        mockMvc.perform(get("/projects/"+ fakeID +"/subprojects"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("views/view-subprojects"))
                .andExpect(model().attribute("project",testProject))
                .andExpect(model().attribute("totalHours", 67))
                .andExpect(model().attribute("subProjects", testSubList));
    }
}