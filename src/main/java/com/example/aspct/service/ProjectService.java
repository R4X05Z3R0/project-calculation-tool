package com.example.aspct.service;

import com.example.aspct.model.Project;
import com.example.aspct.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
//  Service - Gemmer Projekter og deres Sub-projekter i Databasen.

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SubProjectService subProjectService;

    public ProjectService(ProjectRepository projectRepository, SubProjectService subProjectService) {
        this.projectRepository = projectRepository;
        this.subProjectService = subProjectService;
    }

 // Liste af Alle projekter
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

// Kører SubProjekt kommandoen der samler deres enkelte tasks efter at have samlet sub-projekter efter ID.
    public Project getProject(int projectId) {
        Project project = projectRepository.findById(projectId);
        project.setSubProjects(subProjectService.getSubProjectsWithTasks(projectId));
        return project;
    }


    public Project createProject(Project project) {
        return projectRepository.save(project);
    }


    public void updateProject(Project project) {
        projectRepository.update(project);
    }


    public void deleteProject(int projectId) {
        projectRepository.deleteById(projectId);
    }
}