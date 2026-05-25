package com.example.aspct.service;

import com.example.aspct.model.Planner;
import com.example.aspct.model.Project;
import com.example.aspct.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

// Sætter arbejdstiden fra Projektet i en "reel" tidsrelation ifht.
// en arbejdsuge med weekenden inkluderet for at bedre estimere "estimated finish date."

@Service
public class PlannerService {

    private final ProjectRepository projectRepository;
    private final SubProjectService subProjectService;
    private final EmployeeService employeeService;

    public PlannerService(ProjectRepository projectRepository,
                          SubProjectService subProjectService,
                          EmployeeService employeeService) {
        this.projectRepository = projectRepository;
        this.subProjectService = subProjectService;
        this.employeeService = employeeService;
    }

    public Planner generatePlan(int projectId) {
        Project project = projectRepository.findById(projectId);

        double workloadHours =
                subProjectService.getTotalHoursForProject(projectId);

        double workforceDailyHours =
                employeeService.getWorkforceDailyHoursForProject(projectId);

        int workDaysNeeded =
                calculateWorkDays(workloadHours, workforceDailyHours);

        LocalDate expectedFinishDate = null;

        if (workDaysNeeded > 0) {
            expectedFinishDate = addWorkDays(LocalDate.now(), workDaysNeeded);
        }

        boolean onTrack = false;
        int daysOverDeadline = 0;

        if (project.getDeadline() != null && expectedFinishDate != null) {
            onTrack = !expectedFinishDate.isAfter(project.getDeadline());

            if (!onTrack) {
                daysOverDeadline = countWorkDaysBetween(
                        project.getDeadline(),
                        expectedFinishDate
                );
            }
        }

        Planner planner = new Planner();

        planner.setProjectId(projectId);
        planner.setDeadline(project.getDeadline());
        planner.setWorkloadHours(workloadHours);
        planner.setWorkforceDailyHours(workforceDailyHours);
        planner.setWorkDaysNeeded(workDaysNeeded);
        planner.setExpectedFinishDate(expectedFinishDate);
        planner.setOnTrack(onTrack);
        planner.setDaysOverDeadline(daysOverDeadline);

        return planner;
    }
    //Tjekker - "er denne dag en  Arbejdsdag ? "
    private boolean isWorkDay(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();

        return day != DayOfWeek.SATURDAY
                && day != DayOfWeek.SUNDAY;
    }
    private int calculateWorkDays(double workloadHours, double workforceDailyHours) {
        if (workloadHours <= 0) {
            return 0;
        }

        if (workforceDailyHours <= 0) {
            return -1;
        }

        return (int) Math.ceil(workloadHours / workforceDailyHours);
    }

    private LocalDate addWorkDays(LocalDate startDate, int workDays) {
        LocalDate date = startDate;
        int addedDays = 0;

        while (addedDays < workDays) {
            date = date.plusDays(1);

            if (isWorkDay(date)) {
                addedDays++;
            }
        }

        return date;
    }


    private int countWorkDaysBetween(LocalDate from, LocalDate to) {
        int count = 0;
        LocalDate date = from;

        while (date.isBefore(to)) {
            date = date.plusDays(1);

            if (isWorkDay(date)) {
                count++;
            }
        }

        return count;
    }


}