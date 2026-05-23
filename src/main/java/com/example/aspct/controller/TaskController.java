package com.example.aspct.controller;

import com.example.aspct.model.Task;
import com.example.aspct.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /tasks/create-form — Task view form
    @GetMapping("/create-form")
    public String getTask(@RequestParam int subProjectId, Model model) {
        Task newTask = new Task();
        newTask.setSubProjectId(subProjectId);

        model.addAttribute("task", newTask);
        return "create/create-task";
    }

    // POST /tasks — create a task under a sub-project
    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        taskService.createTask(task);

        //noinspection SpringMVCViewInspection - Apparently needed for Qodana to ignore
        return "redirect:/subprojects/" + task.getSubProjectId() +"/tasks";
    }

    // GET /tasks/{id}/edit — Edit form for tasks
    @GetMapping("/{taskId}/edit")
    public String editTask(@PathVariable int taskId, Model model){
        Task taskToEdit = taskService.getTask(taskId);
        model.addAttribute("task", taskToEdit);

        return "edit/edit-task";
    }

    // POST /tasks/{id} — update a task
    @PostMapping("/{taskId}/update")
    public String updateTask(@PathVariable int taskId, @ModelAttribute Task task) {
        task.setTaskId(taskId);
        taskService.updateTask(task);

        //noinspection SpringMVCViewInspection - Apparently needed for Qodana to ignore
        return "redirect:/subprojects/" + task.getSubProjectId() + "/tasks";
    }

    // Post /tasks/{id}/delete — delete a task
    @PostMapping ("/{taskId}/delete")
    public String deleteTask(@PathVariable int taskId, @RequestParam int subProjectId) {
        taskService.deleteTask(taskId);

        //noinspection SpringMVCViewInspection - Apparently needed for Qodana to ignore
        return "redirect:/subprojects/" + subProjectId +"/tasks";
    }
}