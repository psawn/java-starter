package com.example.demo.validation.Course;

import lombok.Data;

import java.util.List;

@Data
public class CreateCourseRequest {
    private List<CourseRequest> courses;
}
