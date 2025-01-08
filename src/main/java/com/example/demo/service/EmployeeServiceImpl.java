package com.example.demo.service;

// import com.example.demo.dao.EmployeeDAO;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    // private final EmployeeDAO employeeDAO;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
        employeeRepository = theEmployeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = employeeRepository.findById(theId);

        Employee theEmployee = null;

        if (result.isPresent()) {
            theEmployee = result.get();
        }

        return theEmployee;
    }

    @Override
    // remove Transactional because JpsRepository provide this functionality
    // @Transactional
    public Employee save(Employee theEmployee) {
        return employeeRepository.save(theEmployee);
    }

    @Override
    // @Transactional
    public void deleteById(int theId) {
        employeeRepository.deleteById(theId);
    }

    @Override
    public List<Employee> findAll(String fieldName, boolean ascending) {
        // return employeeRepository.findAllByOrderByLastNameAsc();

        Sort sortOrder = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, fieldName);

        return employeeRepository.findAll(sortOrder);
    }
}
