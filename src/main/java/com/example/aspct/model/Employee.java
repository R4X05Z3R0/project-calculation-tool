package com.example.aspct.model;

// Employee, har stadig competency_id for senere udvidelser.  

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


    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public int getCompetencyId() { return competencyId; }
    public double getDailyHours() {
        return dailyHours;
    }

    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public void setName(String name) { this.name = name; }
    public void setCompetencyId(int competencyId) { this.competencyId = competencyId; }
    public void setDailyHours(double dailyHours) {
        this.dailyHours = dailyHours;
    }
}