package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(path = "employee")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public List<Employee> findAllByOrderByLastNameAsc();
}
