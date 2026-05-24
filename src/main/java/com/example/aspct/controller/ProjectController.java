package com.example.aspct.controller;

import com.example.aspct.model.Project;
import com.example.aspct.model.SubProject;
import com.example.aspct.service.ProjectService;
import com.example.aspct.service.SubProjectService;
import com.example.aspct.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final SubProjectService subProjectService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService,
                             SubProjectService subProjectService,
                             TaskService taskService) {
        this.projectService = projectService;
        this.subProjectService = subProjectService;
        this.taskService = taskService;
    }

    // GET /projects/ — list all projects (US-2)
    @GetMapping("/")
    public String listProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "views/view-projects";
    }

    //TODO: I can't remember what this is for but we will figure it out
    // GET projects/{id} — view a single project with its total hours (US-3, US-11)
    @GetMapping("/{projectId}")
    public Project viewProject(@PathVariable int projectId) {
        return projectService.getProject(projectId);
    }

    // GET /projects/{id}/total-hours — project total (US-11)
    @GetMapping("/{projectId}/total-hours")
    public double getTotalHours(@PathVariable int projectId) {
        return subProjectService.getTotalHoursForProject(projectId);
    }

    // GET /projects/{id}/subprojects — all sub-projects for a project (US-3)
    @GetMapping("/{projectId}/subprojects")
    public String getSubProjects(@PathVariable int projectId, Model model) {
        Project project = projectService.getProject(projectId);
        int totalHours = (int)subProjectService.getTotalHoursForProject(project.getProjectId());
        List<SubProject> subProjects = subProjectService.getSubProjectsWithTasks(project.getProjectId());

        model.addAttribute("project", project);
        model.addAttribute("totalHours",totalHours);
        model.addAttribute("subProjects", subProjects);
        return "views/view-subprojects";
    }

    // POST /projects/create — create a project (US-1)
    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    // POST projects/{id}/update — update a project (US-4)
    @PostMapping("/{projectId}/update")
    public void updateProject(@PathVariable int projectId, @RequestBody Project project) {
        project.setProjectId(projectId);
        projectService.updateProject(project);
    }

    // DELETE /api/projects/{id}/delete — delete a project and cascade (US-5)
    @PostMapping("/{projectId}/delete")
    public void deleteProject(@PathVariable int projectId) {
        projectService.deleteProject(projectId);
    }
}