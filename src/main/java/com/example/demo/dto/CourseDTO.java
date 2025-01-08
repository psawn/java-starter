package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseDTO {
    private int id;

    private String title;

    private List<StudentDTO> students;

    private List<ReviewDTO> reviews;
}
