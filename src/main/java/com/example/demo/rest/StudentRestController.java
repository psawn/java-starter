package com.example.demo.rest;

import com.example.demo.entity.Student;
import com.example.demo.exception.NotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {
    private List<Student> theStudents;

    // @PostConstruct only run once
    @PostConstruct
    public void loadData() {
        theStudents = new ArrayList<>();

        theStudents.add(new Student("who de hell", "mother faker", "test@gmail.com", 1));
    }

    @GetMapping
    public List<Student> getStudents() {
        return theStudents;
    }

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        if (studentId >= theStudents.size() || studentId < 0) {
            throw new NotFoundException("Student id not found - " + studentId);
        }

        return theStudents.get(studentId);
    }
}