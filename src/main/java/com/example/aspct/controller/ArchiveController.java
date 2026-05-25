package com.example.aspct.controller;

import com.example.aspct.model.Project;
import com.example.aspct.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/archive")
public class ArchiveController {

    private final ProjectService projectService;

    public ArchiveController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // GET /archive/ — Dedicated archive view for archived projects
    @GetMapping("/")
    public String viewArchiveDashboard(Model model) {
        List<Project> archivedProjects = projectService.getAllArchivedProjects();
        model.addAttribute("archivedProjects", archivedProjects);
        return "views/view-archive";
    }

    // POST /archive/project/{id}/archive — archive an active project from the main dashboard
    @PostMapping("/project/{projectId}/archive")
    public String archiveProject(@PathVariable int projectId) {
        projectService.archiveProject(projectId);
        return "redirect:/projects/";
    }

    // POST /archive/project/{id}/restore — Restore project from
    @PostMapping("/project/{projectId}/restore")
    public String restoreProject(@PathVariable int projectId) {
        projectService.restoreProject(projectId);
        return "redirect:/archive/";
    }

    // POST /archive/project/{id}/delete — Permanent delete, Gone, Reduced to ashes
    @PostMapping("/project/{projectId}/delete")
    public String purgeProjectPermanently(@PathVariable int projectId) {
        projectService.deleteProject(projectId);
        return "redirect:/archive/";
    }
}