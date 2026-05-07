package com.example.aspct.controller;

import com.example.aspct.model.Task;
import com.example.aspct.service.TaskService;
import org.springframework.web.bind.annotation.*;

// REST FOUNDATION — test all endpoints with Postman before adding HTML views.
// To evolve to MVC: replace @RestController with @Controller,
// inject Model, return view name strings instead of objects,
// and replace @RequestBody with @ModelAttribute.

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks/{id} — single task (US-8)
    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable int taskId) {
        return taskService.getTask(taskId);
    }

    // POST /api/tasks — create a task under a sub-project (US-8)
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // PUT /api/tasks/{id} — update a task (US-9)
    @PutMapping("/{taskId}")
    public void updateTask(@PathVariable int taskId, @RequestBody Task task) {
        task.setTaskId(taskId);
        taskService.updateTask(task);
    }

    // DELETE /api/tasks/{id} — delete a task (US-9)
    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable int taskId) {
        taskService.deleteTask(taskId);
    }
}