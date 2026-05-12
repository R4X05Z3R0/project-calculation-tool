package com.example.aspct.controller;

import com.example.aspct.model.Project;
import com.example.aspct.model.SubProject;
import com.example.aspct.service.ProjectService;
import com.example.aspct.service.SubProjectService;
import com.example.aspct.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST FOUNDATION — test all endpoints with Postman before adding HTML views.
// To evolve to MVC: replace @RestController with @Controller,
// inject Model, return view name strings instead of objects,
// and replace @RequestBody with @ModelAttribute.

@RestController
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

    // GET /api/projects — list all projects (US-2)
    @GetMapping("/")
    public List<Project> listProjects() {
        return projectService.getAllProjects();
    }

    // GET /projects/{id} — view a single project with its total hours (US-3, US-11)
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
    public List<SubProject> getSubProjects(@PathVariable int projectId) {
        return subProjectService.getSubProjectsByProjectId(projectId);
    }

    // POST /api/projects — create a project (US-1)
    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    // PUT /api/projects/{id} — update a project (US-4)
    @PostMapping("/{projectId}/update")
    public void updateProject(@PathVariable int projectId, @RequestBody Project project) {
        project.setProjectId(projectId);
        projectService.updateProject(project);
    }

    // DELETE /api/projects/{id} — delete a project and cascade (US-5)
    @PostMapping("/{projectId}/delete")
    public void deleteProject(@PathVariable int projectId) {
        projectService.deleteProject(projectId);
    }
}