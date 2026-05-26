package com.example.aspct.model;

import java.time.LocalDate;

// TODO Kan evt udvides med "antal medarbejdere", nu hvor project_worker table eksisterer.

public class Planner {

    private int projectId;
    private LocalDate deadline;

    private double workloadHours;
    private double workforceDailyHours;
    private int workDaysNeeded;

    private LocalDate expectedFinishDate;
    private boolean onTrack;
    private int daysOverDeadline;

    public Planner() {}

    public int getProjectId() { return projectId; }
    public LocalDate getDeadline() { return deadline; }
    public double getWorkloadHours() { return workloadHours; }
    public double getWorkforceDailyHours() { return workforceDailyHours; }
    public int getWorkDaysNeeded() { return workDaysNeeded; }
    public LocalDate getExpectedFinishDate() { return expectedFinishDate; }
    public boolean isOnTrack() { return onTrack; }
    public int getDaysOverDeadline() { return daysOverDeadline; }

    public void setProjectId(int projectId) { this.projectId = projectId; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setWorkloadHours(double workloadHours) { this.workloadHours = workloadHours; }
    public void setWorkforceDailyHours(double workforceDailyHours) { this.workforceDailyHours = workforceDailyHours; }
    public void setWorkDaysNeeded(int workDaysNeeded) { this.workDaysNeeded = workDaysNeeded; }
    public void setExpectedFinishDate(LocalDate expectedFinishDate) { this.expectedFinishDate = expectedFinishDate; }
    public void setOnTrack(boolean onTrack) { this.onTrack = onTrack; }
    public void setDaysOverDeadline(int daysOverDeadline) { this.daysOverDeadline = daysOverDeadline; }
}