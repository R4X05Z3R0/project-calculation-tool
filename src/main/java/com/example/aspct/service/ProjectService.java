package com.example.aspct.service;

import com.example.aspct.model.Project;
import com.example.aspct.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SubProjectService subProjectService;

    public ProjectService(ProjectRepository projectRepository, SubProjectService subProjectService) {
        this.projectRepository = projectRepository;
        this.subProjectService = subProjectService;
    }

    // GET all projects — lists empty here; totals calculated via SubProjectService
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // GET single project with full tree: project → subProjects → tasks (US-3)
    public Project getProject(int projectId) {
        Project project = projectRepository.findById(projectId);
        project.setSubProjects(subProjectService.getSubProjectsWithTasks(projectId));
        return project;
    }

    // CREATE
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // UPDATE
    public void updateProject(Project project) {
        projectRepository.update(project);
    }

    // DELETE (sub-projects and tasks cascade via DB)
    public void deleteProject(int projectId) {
        projectRepository.deleteById(projectId);
    }
}