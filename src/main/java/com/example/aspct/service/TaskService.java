package com.example.aspct.service;

import com.example.aspct.model.Task;
import com.example.aspct.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Service Task - Arbejdsopgaver CRUD.
// Indeholder getTotalHoursForSubProject, der udregner det samlede antal af timer der er på alle Tasks på et givent Sub-projekt.

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // GET all tasks for a sub-project
    public List<Task> getTasksBySubProjectId(int subProjectId) {
        return taskRepository.findBySubProjectId(subProjectId);
    }

    // GET single task
    public Task getTask(int taskId) {
        return taskRepository.findById(taskId);
    }

    // CREATE
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // UPDATE
    public void updateTask(Task task) {
        taskRepository.update(task);
    }

    // DELETE
    public void deleteTask(int taskId) {
        taskRepository.deleteById(taskId);
    }

    // CALCULATE total estimated hours for a sub-project (US-10)
    public double getTotalHoursForSubProject(int subProjectId) {
        List<Task> tasks = taskRepository.findBySubProjectId(subProjectId);
        double total = 0;
        for (Task task : tasks) {
            total += task.getEstimatedHours();
        }
        return total;
    }
}