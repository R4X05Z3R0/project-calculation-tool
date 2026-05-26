package com.example.aspct.model;
//TODO Bruges ikke i denne version, men er stadig til stede til evt. at udvide med specialistroller.

// **GAMMEL KOMMENTAR** Repræsentant for et " Kompetencekrav" - dette kan være en UX/UI, database, eller andet område der kræver
//specielle arbejdsegenskaber der skal bruges til et specifikt projekt.  Dette er modellen for alle  forskellige arbejdstyper og deres
// typer kompetencer.  Indeholder en "dailyCapacityHours" for en samlet fiktiv kapacitet
//   Hver Employee vil have en kompetence i Phase 1 og dette vil erstatte " dailyCapacityHours" fra "kompetencepuljen".




public class Competency {

    private int competencyId;
    private String name;

    private String description;

    public Competency(int competencyId, String name,
                      double dailyCapacityHours, String description) {
        this.competencyId = competencyId;
        this.name = name;

        this.description = description;
    }

    public Competency() {}

    public int getCompetencyId() { return competencyId; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setCompetencyId(int competencyId) { this.competencyId = competencyId; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
}
