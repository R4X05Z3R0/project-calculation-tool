package com.example.aspct.model;


public class Employee {

    private int EmployeeId;
    private String name;
    private int competencyId;
    private double weeklyHours;

    public Employee(int EmployeeId, String name, int competencyId, double weeklyHours) {
        this.EmployeeId = EmployeeId;
        this.name = name;
        this.competencyId = competencyId;
        this.weeklyHours = weeklyHours;
    }

    public Employee() {}

    // Getters
    public int getEmployeeId() { return EmployeeId; }
    public String getName() { return name; }
    public int getCompetencyId() { return competencyId; }
    public double getWeeklyHours() {
        return weeklyHours;
    }
    // Setters
    public void setEmployeeId(int EmployeeId) { this.EmployeeId = EmployeeId; }
    public void setName(String name) { this.name = name; }
    public void setCompetencyId(int competencyId) { this.competencyId = competencyId; }
    public void setWeeklyHours(double weeklyHours) {
        this.weeklyHours = weeklyHours;
    }
}