package com.example.demo.rest;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    @GetMapping
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        return theEmployee;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee theEmployee) {
        theEmployee.setId(0);

        return employeeService.save(theEmployee);
    }

    @PutMapping
    public Employee updateEmployee(@RequestBody Employee theEmployee) {
        return employeeService.save(theEmployee);
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee tempEmployee = employeeService.findById(employeeId);

        if (tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        employeeService.deleteById(employeeId);

        return "Delete employee id - " + employeeId;
    }
}
