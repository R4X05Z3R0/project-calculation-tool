package com.example.aspct.model;


public class CompetencyWorkload {

    private int competencyId;
    private String competencyName;
    private double allocatedHoursPerDay;
    private double totalEstimatedHours;
    private int workDaysNeeded;


    public CompetencyWorkload(int competencyId, String competencyName,
                               double allocatedHoursPerDay, double totalEstimatedHours,
                               int workDaysNeeded) {
        this.competencyId = competencyId;
        this.competencyName = competencyName;
        this.allocatedHoursPerDay = allocatedHoursPerDay;
        this.totalEstimatedHours = totalEstimatedHours;
        this.workDaysNeeded = workDaysNeeded;
    }

    public CompetencyWorkload() {}

    public int getCompetencyId() { return competencyId; }
    public String getCompetencyName() { return competencyName; }
    public double getallocatedHoursPerDay() { return allocatedHoursPerDay; }
    public double getTotalEstimatedHours() { return totalEstimatedHours; }
    public int getWorkDaysNeeded() { return workDaysNeeded; }

    public void setCompetencyId(int competencyId) { this.competencyId = competencyId; }
    public void setCompetencyName(String competencyName) { this.competencyName = competencyName; }
    public void setallocatedHoursPerDay(double allocatedHoursPerDay) { this.allocatedHoursPerDay = allocatedHoursPerDay; }
    public void setTotalEstimatedHours(double totalEstimatedHours) { this.totalEstimatedHours = totalEstimatedHours; }
    public void setWorkDaysNeeded(int workDaysNeeded) { this.workDaysNeeded = workDaysNeeded; }
}