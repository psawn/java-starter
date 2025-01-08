package com.example.demo.validation.Instructor;

import com.example.demo.validation.Course.CourseRequest;
import com.example.demo.validation.InstructorDetail.CreateInstructorDetailRequest;
import lombok.Data;

import java.util.List;

@Data
public class CreateInstructorRequest {
    private String firstName;

    private String lastName;

    private String email;

    private CreateInstructorDetailRequest instructorDetail;

    private List<CourseRequest> courses;
}