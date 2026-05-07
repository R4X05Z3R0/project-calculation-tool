package com.example.aspct.model;

import java.time.LocalDate;

public class Task {

    private int taskId;
    private int subProjectId;
    private String name;
    private double estimatedHours;
    private LocalDate deadline;
    private String description;

    // Constructors
    public Task(int taskId, int subProjectId, String name,
                double estimatedHours, LocalDate deadline, String description) {
        this.taskId = taskId;
        this.subProjectId = subProjectId;
        this.name = name;
        this.estimatedHours = estimatedHours;
        this.deadline = deadline;
        this.description = description;
    }

    public Task() {}

    // Getters
    public int getTaskId() { return taskId; }
    public int getSubProjectId() { return subProjectId; }
    public String getName() { return name; }
    public double getEstimatedHours() { return estimatedHours; }
    public LocalDate getDeadline() { return deadline; }
    public String getDescription() { return description; }

    // Setters
    public void setTaskId(int taskId) { this.taskId = taskId; }
    public void setSubProjectId(int subProjectId) { this.subProjectId = subProjectId; }
    public void setName(String name) { this.name = name; }
    public void setEstimatedHours(double estimatedHours) { this.estimatedHours = estimatedHours; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setDescription(String description) { this.description = description; }
}