package com.example.demo.validation.Course;

import lombok.Data;

import java.util.List;

@Data
public class CourseIdsRequest {
    private List<Integer> courseIds;
}
