package com.example.aspct.service;

import com.example.aspct.model.SubProject;
import com.example.aspct.repository.SubProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Service lag - Gemmer Sub-projekter og indlæser deres tasks fra databasen.
// udregner getTotalHoursForProject baseret på en liste af alle Sub-Projekter.
//

@Service
public class SubProjectService {

    private final SubProjectRepository subProjectRepository;
    private final TaskService taskService;

    public SubProjectService(SubProjectRepository subProjectRepository, TaskService taskService) {
        this.subProjectRepository = subProjectRepository;
        this.taskService = taskService;
    }

    // Laver liste af Sub-projekter med samme ProjectID uden deres tasks.

    public List<SubProject> getSubProjectsByProjectId(int projectId) {
        return subProjectRepository.findByProjectId(projectId);
    }

// Laver liste af Sub-projekter med samme ProjectID. Kører listen igennem og samler alle tasks med en af disse Sub-projekt ID'er.
    public List<SubProject> getSubProjectsWithTasks(int projectId) {
        List<SubProject> subProjects = subProjectRepository.findByProjectId(projectId);
        for (SubProject subProject : subProjects) {
            subProject.setTasks(taskService.getTasksBySubProjectId(subProject.getSubProjectId()));
        }
        return subProjects;
    }

    public SubProject getSubProject(int subProjectId) {
        return subProjectRepository.findById(subProjectId);
    }

    public SubProject createSubProject(SubProject subProject) {
        return subProjectRepository.save(subProject);
    }

    public void updateSubProject(SubProject subProject) {
        subProjectRepository.update(subProject);
    }

    public void deleteSubProject(int subProjectId) {
        subProjectRepository.deleteById(subProjectId);
    }


    // Samler Sub-projekters estimatedhours fra tasks, og giver et samlet estimatedhours for hele Projektet.

    public double getTotalHoursForProject(int projectId) {
        List<SubProject> subProjects = subProjectRepository.findByProjectId(projectId);
        double total = 0;
        for (SubProject subProject : subProjects) {
            total += taskService.getTotalHoursForSubProject(subProject.getSubProjectId());
        }
        return total;
    }
}