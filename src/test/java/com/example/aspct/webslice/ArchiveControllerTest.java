package com.example.aspct.webslice;

import com.example.aspct.controller.ArchiveController;
import com.example.aspct.model.Project;
import com.example.aspct.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArchiveController.class)
public class ArchiveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @Test
    public void testViewArchiveDashboard_LoadsArchivedProjectsAndReturnsCorrectView() throws Exception {

        Project project1 = new Project();
        project1.setProjectId(1);
        project1.setProjectName("Archived Project Alpha");

        Project project2 = new Project();
        project2.setProjectId(2);
        project2.setProjectName("Archived Project Beta");

        List<Project> mockArchived = Arrays.asList(project1, project2);
        when(projectService.getAllArchivedProjects()).thenReturn(mockArchived);

        // Act & Assert
        mockMvc.perform(get("/archive/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("views/view-archive"))
                .andExpect(model().attributeExists("archivedProjects"))
                .andExpect(model().attribute("archivedProjects", mockArchived));

        verify(projectService).getAllArchivedProjects();
    }

    @Test
    public void testArchiveProject_TriggersServiceAndRedirectsToMainDashboard() throws Exception {
        int projectIdToArchive = 42;

        // Act and Assert
        mockMvc.perform(post("/archive/project/" + projectIdToArchive + "/archive")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/"));

        verify(projectService).archiveProject(projectIdToArchive);
    }

    @Test
    public void testRestoreProject_TriggersServiceAndRedirectsToArchiveDashboard() throws Exception {
        int projectIdToRestore = 42;

        // Act and Assert
        mockMvc.perform(post("/archive/project/" + projectIdToRestore + "/restore")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/archive/"));

        verify(projectService).restoreProject(projectIdToRestore);
    }

    @Test
    public void testPurgeProjectPermanently_TriggersServiceAndRedirectsToArchiveDashboard() throws Exception {
        int projectIdToPurge = 42;

        // Act and Assert
        mockMvc.perform(post("/archive/project/" + projectIdToPurge + "/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/archive/")); // Gone, reduced to ashes... So sick

        verify(projectService).deleteProject(projectIdToPurge);
    }
}