package com.example.aspct.integration;

import com.example.aspct.model.Project;
import com.example.aspct.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class ProjectRepoTest {

    @Autowired
    private ProjectRepository repo;

    @Test
    void findAll() {
        List<Project> all = repo.findAll();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(5);
        assertThat(all.get(0).getCompanyName()).isEqualTo("EcoPower Energy");
        assertThat(all.get(4).getCompanyName()).isEqualTo("Acme Ltd");
    }

    @Test
    void findAllActive() {
        List<Project> active = repo.findAllActive();

        assertThat(active).isNotNull();
        assertThat(active.size()).isEqualTo(4);
        assertThat(active.get(3).getCompanyName()).isEqualTo("Acme Ltd");
    }

    @Test
    void findAllArchived() {
        List<Project> archived = repo.findAllArchived();

        assertThat(archived).isNotNull();
        assertThat(archived.size()).isEqualTo(1);
        assertThat(archived.getFirst().getCompanyName()).isEqualTo("Secure FinTech");
    }

    @Test
    void findById() {
        Project project = repo.findById(1);

        assertThat(project).isNotNull();
        assertThat(project.getCompanyName()).isEqualTo("Acme Ltd");
        assertThat(project.getProjectName()).isEqualTo("Website Redesign");
    }

    @Test
    void insertAndReadBack() {
        Project newProject = new Project();
        newProject.setProjectId(6);
        newProject.setCompanyName("Initech");
        newProject.setProjectName("TPS Reports");
        newProject.setDeadline(LocalDate.now().plusDays(30));
        newProject.setDescription("Fixing the cover sheets.");

        Project saved = repo.save(newProject);
        Project retrieved = repo.findById(saved.getProjectId());

        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getCompanyName()).isEqualTo("Initech");
        assertThat(retrieved.getProjectName()).isEqualTo("TPS Reports");
    }

    @Test
    void update() {
        Project project = repo.findById(1);
        project.setCompanyName("New Acme Ltd");
        project.setProjectName("Advanced UI Redesign");

        repo.update(project);

        Project updated = repo.findById(1);
        assertThat(updated.getCompanyName()).isEqualTo("New Acme Ltd");
        assertThat(updated.getProjectName()).isEqualTo("Advanced UI Redesign");
    }

    @Test
    void archiveById() {
        repo.archiveById(1);

        Project project = repo.findById(1);
        assertThat(project.isArchived()).isTrue();
    }

    @Test
    void restoreById() {
        repo.restoreById(4);

        Project project = repo.findById(4);
        assertThat(project.isArchived()).isFalse();
    }

    @Test
    void deleteById() {
        repo.deleteById(1);

        assertThrows(Exception.class, () -> repo.findById(1));
    }
}