package com.example.aspct.model;

// Repræsentant for et " Kompetencekrav" - dette kan være en UX/UI, database, eller andet område der kræver
//specielle arbejdsegenskaber der skal bruges til et specifikt projekt.  Dette er modellen for alle  forskellige
// typer kompetencer.  Dette er selve kompetencen.  Hver Employee vil have en kompetence i Phase1.2 og dette vil erstatte " dailyCapacityHours".


public class Competency {

    private int competencyId;
    private String name;
    private double dailyCapacityHours;
    private String description;

    public Competency(int competencyId, String name,
                      double dailyCapacityHours, String description) {
        this.competencyId = competencyId;
        this.name = name;
        this.dailyCapacityHours = dailyCapacityHours;
        this.description = description;
    }

    public Competency() {}

    public int getCompetencyId() { return competencyId; }
    public String getName() { return name; }
    public double getDailyCapacityHours() { return dailyCapacityHours; }
    public String getDescription() { return description; }

    public void setCompetencyId(int competencyId) { this.competencyId = competencyId; }
    public void setName(String name) { this.name = name; }
    public void setDailyCapacityHours(double dailyCapacityHours) { this.dailyCapacityHours = dailyCapacityHours; }
    public void setDescription(String description) { this.description = description; }
}
