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

    @GetMapping("/{subProjectId}")
    public SubProject getSubProject(@PathVariable int subProjectId) {
        return subProjectService.getSubProject(subProjectId);
    }

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


    @PostMapping
    public SubProject createSubProject(@RequestBody SubProject subProject) {
        return subProjectService.createSubProject(subProject);
    }


    @PostMapping("/{subProjectId}/update")
    public void updateSubProject(@PathVariable int subProjectId, @RequestBody SubProject subProject) {
        subProject.setSubProjectId(subProjectId);
        subProjectService.updateSubProject(subProject);
    }


    @PostMapping("/{subProjectId}/delete")
    public void deleteSubProject(@PathVariable int subProjectId) {
        subProjectService.deleteSubProject(subProjectId);
    }
}