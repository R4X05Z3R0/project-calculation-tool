package com.example.aspct.controller;

import com.example.aspct.model.SubProject;
import com.example.aspct.model.Task;
import com.example.aspct.service.SubProjectService;
import com.example.aspct.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subprojects")
public class SubProjectController {

    private final SubProjectService subProjectService;
    private final TaskService taskService;

    public SubProjectController(SubProjectService subProjectService, TaskService taskService) {
        this.subProjectService = subProjectService;
        this.taskService = taskService;
    }

    // GET /subprojects/create-form - Shows create form for subproject
    @GetMapping("/create-form")
    public String getSubProject(@RequestParam int projectId, Model model) {
        SubProject newSubproject = new SubProject();
        newSubproject.setProjectId(projectId);

        model.addAttribute("subProject", newSubproject);
        return "create/create-subproject";
    }

    // GET /subprojects/{id}/total-hours — sub-project total
    @GetMapping("/{subProjectId}/total-hours")
    public double getTotalHours(@PathVariable int subProjectId) {
        return taskService.getTotalHoursForSubProject(subProjectId);
    }

    // GET /subprojects/{id}/tasks — all tasks for a sub-project
    @GetMapping("/{subProjectId}/tasks")
    public String getTasks(@PathVariable int subProjectId, Model model) {
        SubProject subProject = subProjectService.getSubProject(subProjectId);
        List<Task> tasks = taskService.getTasksBySubProjectId(subProjectId);
        int totalHours = (int)taskService.getTotalHoursForSubProject(subProjectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("subProject", subProject);
        model.addAttribute("totalHours", totalHours);
        return "views/view-tasks";
    }

    //GET /subprojects/id/edit-form - Edit form for subproject
    @GetMapping("/{subProjectId}/edit-form")
    public String editSubProject(@PathVariable int subProjectId, Model model){
        SubProject subProjectToEdit = subProjectService.getSubProject(subProjectId);
        model.addAttribute("subProject", subProjectToEdit);

        return "edit/edit-subprojects";
    }

    // POST /subprojects/create — create a sub-project under a project
    @PostMapping("/create")
    public String createSubProject(@ModelAttribute SubProject subProject) {
        subProjectService.createSubProject(subProject);

        //noinspection SpringMVCViewInspection - Apparently needed for Qodana to ignore
        return "redirect:/projects/" + subProject.getProjectId() +"/subprojects";
    }

    // POST /subprojects/{id}/update — update a sub-project
    @PostMapping("/{subProjectId}/update")
    public String updateSubProject(@PathVariable int subProjectId, @ModelAttribute SubProject subProject) {
        subProject.setSubProjectId(subProjectId);
        subProjectService.updateSubProject(subProject);

        //noinspection SpringMVCViewInspection - Apparently needed for Qodana to ignore
        return "redirect:/projects/" + subProject.getProjectId() + "/subprojects";
    }

    // DELETE /subprojects/{id}/delete — delete a sub-project and cascade tasks
    @PostMapping("/{subProjectId}/delete")
    public String deleteSubProject(@PathVariable int subProjectId, @RequestParam int projectId) {
        subProjectService.deleteSubProject(subProjectId);

        //noinspection SpringMVCViewInspection - Apparently needed for Qodana to ignore
        return "redirect:/projects/" + projectId + "/subprojects";
    }
}