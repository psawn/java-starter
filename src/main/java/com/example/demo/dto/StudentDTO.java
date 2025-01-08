package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private List<CourseDTO> courses;
}
