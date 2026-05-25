package com.example.aspct.service;

import com.example.aspct.exceptions.ResourceNotFoundException;
import com.example.aspct.model.Project;
import com.example.aspct.repository.ProjectRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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

    //I am adding the comments so I don't get confused... It isn't AI. Just saying.

    // GET all projects — lists empty here; totals calculated via SubProjectService

    public List<Project> getAllProjects() {
        return projectRepository.findAll();

    }
    // GET all active projects for the main workspace dashboard
    public List<Project> getAllActiveProjects(){
        return projectRepository.findAllActive();
    }

    // GET all archived projects for the archive view
    public List<Project> getAllArchivedProjects(){
        return projectRepository.findAllArchived();
    }

    // Archive project by id (Soft Delete)
    public void archiveProject(int projectId){
        projectRepository.archiveById(projectId);
    }

    // Reactivate project by id (Restore)
    public void restoreProject(int projectId){
        projectRepository.restoreById(projectId);
    }

    // GET single project with full tree: project -> subProjects -> tasks
    public Project getProject(int projectId) {
        try {
            Project project = projectRepository.findById(projectId);
            project.setSubProjects(subProjectService.getSubProjectsWithTasks(projectId));
            return project;
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID: " + projectId + " was not found");
        }
    }

    // CREATE
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // UPDATE
    public void updateProject(Project project) {
        projectRepository.update(project);
    }

    // DELETE permanently (sub-projects and tasks cascade via DB)
    public void deleteProject(int projectId) {
        projectRepository.deleteById(projectId);
    }
}