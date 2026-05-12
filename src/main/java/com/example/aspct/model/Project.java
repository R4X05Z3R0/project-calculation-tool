package com.example.aspct.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

    public class Project {

        private int projectId;
        private String companyName;
        private String projectName;
        private LocalDate deadline;
        private String description;
        private LocalDateTime createdAt;
        private List<SubProject> subProjects = new ArrayList<>();

        // Constructors

        public Project(int projectId, String companyName, String projectName,
                       LocalDate deadline, String description, LocalDateTime createdAt) {
            this.projectId = projectId;
            this.companyName = companyName;
            this.projectName = projectName;
            this.deadline = deadline;
            this.description = description;
            this.createdAt
                    = createdAt;
        }

        public Project(String companyName, String projectName, LocalDate deadline, String description){
            this.companyName = companyName;
            this.projectName = projectName;
            this.deadline = deadline;
            this.description = description;

        }

        public Project() {}
        // Getters
        public int getProjectId() { return projectId; }
        public String getCompanyName() { return companyName; }
        public String getProjectName() { return projectName; }
        public LocalDate getDeadline() { return deadline; }
        public String getDescription() { return description; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<SubProject> getSubProjects() { return subProjects; }

        // Setters
        public void setProjectId(int projectId) { this.projectId = projectId; }
        public void setCompanyName(String companyName) { this.companyName = companyName; }
        public void setProjectName(String projectName) { this.projectName = projectName; }
        public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
        public void setDescription(String description) { this.description = description; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public void setSubProjects(List<SubProject> subProjects) { this.subProjects = subProjects; }
}
