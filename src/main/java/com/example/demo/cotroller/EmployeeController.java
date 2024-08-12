package com.example.demo.cotroller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // Create a new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // Update an existing employee
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setRole(updatedEmployee.getRole());
                    employee.setEmail(updatedEmployee.getEmail());
                    employee.setMobileNumber(updatedEmployee.getMobileNumber());
                    employee.setPassword(updatedEmployee.getPassword());
                    return employeeRepository.save(employee);
                })
                .orElse(null);
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

    // Login with email and password
    @PostMapping("/login")
    public Employee login(@RequestBody Employee loginRequest) {
        return employeeRepository.findByEmail(loginRequest.getEmail())
                .filter(employee -> employee.getPassword().equals(loginRequest.getPassword()))
                .orElse(null);
    }
}
