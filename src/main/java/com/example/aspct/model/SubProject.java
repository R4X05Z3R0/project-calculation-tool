package com.example.aspct.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// 2. byggeblok i tidsestimeringen. Kompetence/arbejdsområder med Tid (Tasks).
// Et underområde af Arbejdsfordelingen til projektet. Dette bærer "kompetencen"
public class SubProject {

    private int subProjectId;
    private int projectId;
    private String name;
    private LocalDate deadline;
    private String description;
    private List<Task> tasks = new ArrayList<>();

    // Constructors
    public SubProject(int subProjectId, int projectId, String name,
                      LocalDate deadline, String description) {
        this.subProjectId = subProjectId;
        this.projectId = projectId;
        this.name = name;
        this.deadline = deadline;
        this.description = description;
    }

    public SubProject() {}


    public int getSubProjectId() { return subProjectId; }
    public int getProjectId() { return projectId; }
    public String getName() { return name; }
    public LocalDate getDeadline() { return deadline; }
    public String getDescription() { return description; }
    public List<Task> getTasks() { return tasks; }


    public void setSubProjectId(int subProjectId) { this.subProjectId = subProjectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
    public void setName(String name) { this.name = name; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setDescription(String description) { this.description = description; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }
}