package com.example.aspct.controller;

import com.example.aspct.model.SubProject;
import com.example.aspct.model.Task;
import com.example.aspct.service.SubProjectService;
import com.example.aspct.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST FOUNDATION — test all endpoints with Postman before adding HTML views.
// To evolve to MVC: replace @RestController with @Controller,
// inject Model, return view name strings instead of objects,
// and replace @RequestBody with @ModelAttribute.

@Controller
@RequestMapping("/subprojects")
public class SubProjectController {

    private final SubProjectService subProjectService;
    private final TaskService taskService;

    public SubProjectController(SubProjectService subProjectService, TaskService taskService) {
        this.subProjectService = subProjectService;
        this.taskService = taskService;
    }

    // GET /subprojects/{id} — single sub-project (US-3)
    @GetMapping("/{subProjectId}")
    public SubProject getSubProject(@PathVariable int subProjectId) {
        return subProjectService.getSubProject(subProjectId);
    }

    // GET /api/subprojects/{id}/total-hours — sub-project total (US-10)
    @GetMapping("/{subProjectId}/total-hours")
    public double getTotalHours(@PathVariable int subProjectId) {
        return taskService.getTotalHoursForSubProject(subProjectId);
    }

    // GET /api/subprojects/{id}/tasks — all tasks for a sub-project
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

    // POST /api/subprojects — create a sub-project under a project (US-6)
    @PostMapping
    public SubProject createSubProject(@RequestBody SubProject subProject) {
        return subProjectService.createSubProject(subProject);
    }

    // PUT /api/subprojects/{id} — update a sub-project (US-7)
    @PostMapping("/{subProjectId}/update")
    public void updateSubProject(@PathVariable int subProjectId, @RequestBody SubProject subProject) {
        subProject.setSubProjectId(subProjectId);
        subProjectService.updateSubProject(subProject);
    }

    // DELETE /api/subprojects/{id} — delete a sub-project and cascade tasks (US-7)
    @PostMapping("/{subProjectId}/delete")
    public void deleteSubProject(@PathVariable int subProjectId) {
        subProjectService.deleteSubProject(subProjectId);
    }
}