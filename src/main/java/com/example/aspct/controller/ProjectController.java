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

//    // GET /projects/ — list all projects
//    @GetMapping("/")
//    public String listProjects(Model model) {
//        List<Project> projects = projectService.getAllProjects();
//        model.addAttribute("projects", projects);
//        return "views/view-projects";
//    }

    @GetMapping("/")
    public String listProjects(Model model) {
        List<Project> projects = projectService.getAllActiveProjects();
        model.addAttribute("projects", projects);
        return "views/view-projects";
    }


    // GET projects/create — view a single project with its total hours
    @GetMapping("/create-form")
    public String createProject(Model model) {
        model.addAttribute("project", new Project());
        return "create/create-project" ;
    }

    // GET /projects/{id}/total-hours — project total
    @GetMapping("/{projectId}/total-hours")
    public double getTotalHours(@PathVariable int projectId) {
        return subProjectService.getTotalHoursForProject(projectId);
    }

    // GET /projects/{id}/subprojects — all sub-projects for a project
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

    //GET /projects/{id}/edit-form - Shows edit form for project info
    @GetMapping("/{projectId}/edit-form")
    public String editProject(@PathVariable int projectId, Model model){
        Project projectToEdit = projectService.getProject(projectId);
        model.addAttribute("project", projectToEdit);
        return "edit/edit-project";
    }

    // POST /projects/create — create a project
    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project) {
        projectService.createProject(project);

        return "redirect:/projects/";
    }

    // POST projects/{id}/update — update a project
    @PostMapping("/{projectId}/update")
    public String updateProject(@PathVariable int projectId,
                                @ModelAttribute Project project) {

        project.setProjectId(projectId);
        projectService.updateProject(project);

        return "redirect:/projects/";
    }

    // DELETE /projects/{id}/delete — delete a project and cascade
    @PostMapping("/{projectId}/delete")
    public void deleteProject(@PathVariable int projectId) {
        projectService.deleteProject(projectId);
    }
}