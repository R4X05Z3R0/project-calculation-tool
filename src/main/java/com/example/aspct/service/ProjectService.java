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


    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }


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

    // DELETE (sub-projects and tasks cascade via DB)
    public void deleteProject(int projectId) {
        projectRepository.deleteById(projectId);
    }
}