package com.example.aspct.model;


public class Competency {

    private int competencyId;
    private String competencyName;
    private double dailyCapacityHours;
    private double totalEstimatedHours;
    private int workDaysNeeded;

    public Competency(int competencyId, String competencyName,
                               double dailyCapacityHours, double totalEstimatedHours,
                               int workDaysNeeded) {
        this.competencyId = competencyId;
        this.competencyName = competencyName;
        this.dailyCapacityHours = dailyCapacityHours;
        this.totalEstimatedHours = totalEstimatedHours;
        this.workDaysNeeded = workDaysNeeded;
    }

    public Competency() {}

    // Getters
    public int getCompetencyId() { return competencyId; }
    public String getCompetencyName() { return competencyName; }
    public double getDailyCapacityHours() { return dailyCapacityHours; }
    public double getTotalEstimatedHours() { return totalEstimatedHours; }
    public int getWorkDaysNeeded() { return workDaysNeeded; }

    // Setters
    public void setCompetencyId(int competencyId) { this.competencyId = competencyId; }
    public void setCompetencyName(String competencyName) { this.competencyName = competencyName; }
    public void setDailyCapacityHours(double dailyCapacityHours) { this.dailyCapacityHours = dailyCapacityHours; }
    public void setTotalEstimatedHours(double totalEstimatedHours) { this.totalEstimatedHours = totalEstimatedHours; }
    public void setWorkDaysNeeded(int workDaysNeeded) { this.workDaysNeeded = workDaysNeeded; }
}