package com.example.aspct.controller;

import com.example.aspct.model.Task;
import com.example.aspct.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// REST FOUNDATION — test all endpoints with Postman before adding HTML views.
// To evolve to MVC: replace @RestController with @Controller,
// inject Model, return view name strings instead of objects,
// and replace @RequestBody with @ModelAttribute.

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /tasks/{id} — single task (US-8)
    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable int taskId) {
        return taskService.getTask(taskId);
    }

    // POST /tasks — create a task under a sub-project (US-8)
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // GET /tasks/{id}/edit — Edit form for tasks
    @GetMapping("/{taskId}/edit")
    public String editTask(@PathVariable int taskId, Model model){
        Task taskToEdit = taskService.getTask(taskId);
        model.addAttribute("task", taskToEdit);

        return "edit/edit-task";
    }

    // POST /tasks/{id} — update a task (US-9)
    @PostMapping("/{taskId}/update")
    public String updateTask(@PathVariable int taskId, @ModelAttribute Task task) {
        task.setTaskId(taskId);
        taskService.updateTask(task);

        return "redirect:" + ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/subprojects/{subProjectId}/edit-form")
                .buildAndExpand(task.getSubProjectId())
                .toUriString();
    }

    // Post /tasks/{id} — delete a task (US-9)
    @PostMapping ("/{taskId}/delete")
    public void deleteTask(@PathVariable int taskId) {
        taskService.deleteTask(taskId);
    }
}