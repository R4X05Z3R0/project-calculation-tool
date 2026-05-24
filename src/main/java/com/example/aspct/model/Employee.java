package com.example.aspct.model;


public class Employee {

    private int employeeId;
    private String name;
    private int competencyId;
    private double dailyHours;

    public Employee(int employeeId, String name, int competencyId, double dailyHours) {
        this.employeeId = employeeId;
        this.name = name;
        this.competencyId = competencyId;
        this.dailyHours = dailyHours;
    }

    public Employee() {}

    // Getters
    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public int getCompetencyId() { return competencyId; }
    public double getDailyHours() {
        return dailyHours;
    }
    // Setters
    public void setEmployeeId(int EmployeeId) { this.employeeId = EmployeeId; }
    public void setName(String name) { this.name = name; }
    public void setCompetencyId(int competencyId) { this.competencyId = competencyId; }
    public void setDailyHours(double dailyHours) {
        this.dailyHours = dailyHours;
    }
}