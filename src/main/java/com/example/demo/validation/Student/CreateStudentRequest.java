package com.example.demo.validation.Student;

import lombok.Data;

import java.util.List;

@Data
public class CreateStudentRequest {
    private List<StudentRequest> students;
}
