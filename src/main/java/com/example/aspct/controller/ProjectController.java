package com.example.aspct.controller;



import com.example.aspct.model.Planner;
import com.example.aspct.model.Project;
import com.example.aspct.model.SubProject;
import com.example.aspct.service.ProjectService;
import com.example.aspct.service.SubProjectService;
import com.example.aspct.service.TaskService;
import com.example.aspct.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.aspct.service.PlannerService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final SubProjectService subProjectService;
    private final PlannerService plannerService;
    private final EmployeeService employeeService;

    public ProjectController(ProjectService projectService,
                             SubProjectService subProjectService,
                             PlannerService plannerService,
                             EmployeeService employeeService) {
        this.projectService = projectService;
        this.subProjectService = subProjectService;
        this.plannerService = plannerService;
        this.employeeService = employeeService;
    }

    // GET /projects/ — list all projects (US-2)
    @GetMapping("/")
    public String listProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "views/view-projects";
    }


    // GET projects/create — view a single project with its total hours (US-3, US-11)
    @GetMapping("/create-form")
    public String createProject(Model model) {
        model.addAttribute("project", new Project());
        return "create/create-project" ;
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

    //GET /projects/{id}/edit-form - Shows edit form for project info
    @GetMapping("/{projectId}/edit-form")
    public String editProject(@PathVariable int projectId, Model model){
        Project projectToEdit = projectService.getProject(projectId);
        model.addAttribute("project", projectToEdit);
        return "edit/edit-project";
    }

    // POST /projects/create — create a project (US-1)
    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project) {
        projectService.createProject(project);

        return "redirect:/projects/";
    }

    // POST projects/{id}/update — update a project (US-4)
    @PostMapping("/{projectId}/update")
    public String updateProject(@PathVariable int projectId,
                                @ModelAttribute Project project) {

        project.setProjectId(projectId);
        projectService.updateProject(project);

        return "redirect:/projects/";
    }

    // DELETE /projects/{id}/delete — delete a project and cascade (US-5)
    @PostMapping("/{projectId}/delete")
    public void deleteProject(@PathVariable int projectId) {
        projectService.deleteProject(projectId);
    }



    // WORKFORCE - her samler vi (sammen med HTML siden) fremvisningen af alle de matematiske funktioner og behandlinger af data
    // som der bliver lavet både med Arbejdsstyrke og arbejdsdage for et projekt

    @GetMapping("/{projectId}/workforce")
    public String viewWorkforce(@PathVariable int projectId, Model model) {
        Project project = projectService.getProject(projectId);
        Planner planner = plannerService.generatePlan(projectId);

        model.addAttribute("project", project);
        model.addAttribute("planner", planner);
        model.addAttribute("workforce", employeeService.getProjectWorkforce(projectId));
        model.addAttribute("availableEmployees", employeeService.getAvailableEmployeesForProject(projectId));
        model.addAttribute("workforceDailyHours", employeeService.getWorkforceDailyHoursForProject(projectId));

        return "views/view-workforce";
    }
 // Tilføjer en medarbejder til ProjektWorkforce. -> backend (project_employee)
    @PostMapping("/{projectId}/workforce/add")
    public String addEmployeeToProject(@PathVariable int projectId,
                                       @RequestParam int employeeId) {
        employeeService.addEmployeeToProject(projectId, employeeId);

        return "redirect:/projects/" + projectId + "/workforce";
    }

    // fjerner en medarbejder fra Projektets workforce.(project_employee)
    @PostMapping("/{projectId}/workforce/remove")
    public String removeEmployeeFromProject(@PathVariable int projectId,
                                            @RequestParam int employeeId) {
        employeeService.removeEmployeeFromProject(projectId, employeeId);

        return "redirect:/projects/" + projectId + "/workforce";
    }
}