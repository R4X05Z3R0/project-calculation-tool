package com.example.aspct.integration;

import com.example.aspct.model.SubProject;
import com.example.aspct.repository.SubProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class SubProjectRepoTest {

    @Autowired
    private SubProjectRepository repo;

    @Test
    void findByProjectId() {
        List<SubProject> subprojects = repo.findByProjectId(1);

        assertThat(subprojects).isNotNull();
        assertThat(subprojects.size()).isEqualTo(2);
        assertThat(subprojects.get(0).getName()).isEqualTo("Frontend Development");
        assertThat(subprojects.get(1).getName()).isEqualTo("Backend API");
    }

    @Test
    void findById() {
        SubProject subproject = repo.findById(1);

        assertThat(subproject).isNotNull();
        assertThat(subproject.getProjectId()).isEqualTo(1);
        assertThat(subproject.getName()).isEqualTo("Frontend Development");
    }

    @Test
    void insertAndReadBack() {
        SubProject newSubProject = new SubProject();

        newSubProject.setProjectId(1);
        newSubProject.setName("Security Framework");
        newSubProject.setDeadline(LocalDate.now().plusDays(14));
        newSubProject.setDescription("Implementing OAuth2 protocols.");

        SubProject saved = repo.save(newSubProject);
        SubProject retrieved = repo.findById(saved.getSubProjectId());

        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getName()).isEqualTo("Security Framework");
        assertThat(retrieved.getProjectId()).isEqualTo(1);
    }

    @Test
    void update() {
        SubProject subproject = repo.findById(1);
        subproject.setName("Advanced Frontend Development");
        subproject.setDescription("Refactoring view elements.");

        repo.update(subproject);

        SubProject updated = repo.findById(1);
        assertThat(updated.getName()).isEqualTo("Advanced Frontend Development");
        assertThat(updated.getDescription()).isEqualTo("Refactoring view elements.");
    }

    @Test
    void deleteById() {
        repo.deleteById(1);

        assertThrows(EmptyResultDataAccessException.class, () -> repo.findById(1));
    }
}