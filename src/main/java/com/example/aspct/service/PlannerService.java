package com.example.aspct.service;

import com.example.aspct.model.Competency;
import com.example.aspct.model.Project;
import com.example.aspct.model.Planner;
import com.example.aspct.repository.PlannerRepository;
import com.example.aspct.repository.ProjectRepository;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final ProjectRepository projectRepository;

    public PlannerService(PlannerRepository plannerRepository,
                           ProjectRepository projectRepository) {
        this.plannerRepository = plannerRepository;
        this.projectRepository = projectRepository;
    }

    public Planner generatePlan(int projectId) {
        Project project = projectRepository.findById(projectId);
        List<Competency> breakdowns =
                plannerRepository.findHoursPerCompetencyByProject(projectId);
        double unassignedHours =
                plannerRepository.findUnassignedHoursByProject(projectId);

        String bottleneck = "None";
        int maxWorkDays = 0;

        for (Competency breakdown : breakdowns) {
            int workDays = calculateWorkDays(
                    breakdown.getTotalEstimatedHours(),
                    breakdown.getDailyCapacityHours());
            breakdown.setWorkDaysNeeded(workDays);

            if (workDays > maxWorkDays) {
                maxWorkDays = workDays;
                bottleneck = breakdown.getCompetencyName();
            }
        }

        // Project the finish date from today, skipping weekends
        LocalDate startDate = LocalDate.now();
        LocalDate expectedFinish = addWorkDays(startDate, maxWorkDays);

        // Compare against deadline
        boolean onTrack = true;
        int daysOver = 0;

        if (project.getDeadline() != null) {
            onTrack = !expectedFinish.isAfter(project.getDeadline());
            if (!onTrack) {
                daysOver = countWorkDaysBetween(project.getDeadline(), expectedFinish);
            }
        }

        Planner plan = new Planner();
        plan.setProjectId(projectId);
        plan.setDeadline(project.getDeadline());
        plan.setBreakdowns(breakdowns);
        plan.setUnassignedHours(unassignedHours);
        plan.setBottleneckCompetency(bottleneck);
        plan.setMaxWorkDays(maxWorkDays);
        plan.setExpectedFinishDate(expectedFinish);
        plan.setOnTrack(onTrack);
        plan.setDaysOverDeadline(daysOver);

        return plan;
    }

    // Divide total hours by daily capacity, round up (a partial day is still a full day)
    private int calculateWorkDays(double totalHours, double dailyCapacity) {
        if (dailyCapacity <= 0) {
            return 0;
        }
        return (int) Math.ceil(totalHours / dailyCapacity);
    }

    // Add N work days to a start date, skipping weekends
    private LocalDate addWorkDays(LocalDate start, int workDays) {
        LocalDate date = start;
        int added = 0;
        while (added < workDays) {
            date = date.plusDays(1);
            if (isWorkDay(date)) {
                added++;
            }
        }
        return date;
    }

    // Count work days between two dates (exclusive of start, inclusive of end)
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

    private boolean isWorkDay(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }
}