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

    public double getTotalDailyHours() {
        List<Employee> employees = employeeRepository.findAll();

        double totalDailyHours = 0;

        for (Employee employee : employees) {
            totalDailyHours += employee.getDailyHours();
        }

        return totalDailyHours;
    }
}