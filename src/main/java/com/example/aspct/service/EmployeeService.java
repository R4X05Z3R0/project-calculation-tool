package com.example.aspct.service;

import com.example.aspct.model.Employee;
import com.example.aspct.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void addEmployeeToProject(int projectId, int employeeId) {
        employeeRepository.addEmployeeToProject(projectId, employeeId);
    }

    public void removeEmployeeFromProject(int projectId, int employeeId) {
        employeeRepository.removeEmployeeFromProject(projectId, employeeId);
    }


    public List<Employee> getProjectWorkforce(int projectId) {
        return employeeRepository.findEmployeesByProjectId(projectId);
    }

    public List<Employee> getAvailableEmployeesForProject(int projectId) {
        return employeeRepository.findEmployeesNotOnProject(projectId);
    }

    public double getWorkforceDailyHoursForProject(int projectId) {
        List<Employee> employees = employeeRepository.findEmployeesByProjectId(projectId);

        double workforceDailyHours = 0;

        for (Employee employee : employees) {
            workforceDailyHours += employee.getDailyHours();
        }

        return workforceDailyHours;
    }
}