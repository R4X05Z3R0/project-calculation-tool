package com.example.aspct.model;

import java.time.LocalDate;
// Vigtigeste "grundsten" i Tidsestimeringen
// Selve de enkelte arbejdsopgaver som skal laves for at færdiggøre projektet.
// Indeholder Tiden der bruges til at udregne Arbejdstiden i både SubProjekt og Projekt. - estimatedHours
// Er bundet til et Subprojekt og ikke direkte til et projekt.  Har en intern deadline pt.i forberedelse til senere implementeringer.
public class Task {

    private int taskId;
    private int subProjectId;
    private String name;
    private double estimatedHours;
    private LocalDate deadline;
    private String description;
    private Integer competencyId;

    // Constructors
    public Task(int taskId, int subProjectId, String name,
                double estimatedHours, LocalDate deadline, String description, Integer competencyId) {
        this.taskId = taskId;
        this.subProjectId = subProjectId;
        this.name = name;
        this.estimatedHours = estimatedHours;
        this.deadline = deadline;
        this.description = description;
        this.competencyId = competencyId;
    }

    public Task() {}

    public Integer getCompetencyId() { return competencyId; }
    public int getTaskId() { return taskId; }
    public int getSubProjectId() { return subProjectId; }
    public String getName() { return name; }
    public double getEstimatedHours() { return estimatedHours; }
    public LocalDate getDeadline() { return deadline; }
    public String getDescription() { return description; }

    public void setCompetencyId(Integer competencyId) { this.competencyId = competencyId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }
    public void setSubProjectId(int subProjectId) { this.subProjectId = subProjectId; }
    public void setName(String name) { this.name = name; }
    public void setEstimatedHours(double estimatedHours) { this.estimatedHours = estimatedHours; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setDescription(String description) { this.description = description; }
}