package com.example.aspct.service;

import com.example.aspct.model.Employee;
import com.example.aspct.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;


// Håndterer employees, primært i projekt og Workforce relation.
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    //Tilføjer til project_employee tabellen i databasen
    public void addEmployeeToProject(int projectId, int employeeId) {
        employeeRepository.addEmployeeToProject(projectId, employeeId);
    }
    //Fjerner til project_employee tabellen i databasen
    public void removeEmployeeFromProject(int projectId, int employeeId) {
        employeeRepository.removeEmployeeFromProject(projectId, employeeId);
    }

// bruger project_employee tabellen i databasen til at returnere en liste af employee's tilføjet til et givent projekt gennem " addEmployeeToProject" metoden.
    public List<Employee> getProjectWorkforce(int projectId) {
        return employeeRepository.findEmployeesByProjectId(projectId);
    }
    // bruger project_employee tabellen i databasen til at returnere en liste af employee's tilføjet til et givent projekt gennem " addEmployeeToProject" metoden.
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