package com.example.aspct.service;

import com.example.aspct.model.SubProject;
import com.example.aspct.repository.SubProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubProjectService {

    private final SubProjectRepository subProjectRepository;
    private final TaskService taskService;

    public SubProjectService(SubProjectRepository subProjectRepository, TaskService taskService) {
        this.subProjectRepository = subProjectRepository;
        this.taskService = taskService;
    }

    // GET all sub-projects for a project (lists empty — used for flat operations)
    public List<SubProject> getSubProjectsByProjectId(int projectId) {
        return subProjectRepository.findByProjectId(projectId);
    }

    // GET all sub-projects with their tasks populated (used when building the full project tree)
    public List<SubProject> getSubProjectsWithTasks(int projectId) {
        List<SubProject> subProjects = subProjectRepository.findByProjectId(projectId);
        for (SubProject subProject : subProjects) {
            subProject.setTasks(taskService.getTasksBySubProjectId(subProject.getSubProjectId()));
        }
        return subProjects;
    }

    // GET single sub-project
    public SubProject getSubProject(int subProjectId) {
        return subProjectRepository.findById(subProjectId);
    }

    // CREATE
    public SubProject createSubProject(SubProject subProject) {
        return subProjectRepository.save(subProject);
    }

    // UPDATE
    public void updateSubProject(SubProject subProject) {
        subProjectRepository.update(subProject);
    }

    // DELETE (tasks cascade via DB)
    public void deleteSubProject(int subProjectId) {
        subProjectRepository.deleteById(subProjectId);
    }

    // CALCULATE total estimated hours for a project (US-11)
    public double getTotalHoursForProject(int projectId) {
        List<SubProject> subProjects = subProjectRepository.findByProjectId(projectId);
        double total = 0;
        for (SubProject subProject : subProjects) {
            total += taskService.getTotalHoursForSubProject(subProject.getSubProjectId());
        }
        return total;
    }
}