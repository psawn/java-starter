package com.example.demo.dao;

import com.example.demo.entity.Course;
import com.example.demo.entity.Instructor;

import java.util.List;

public interface InstructorDAO {
    void save(Instructor instructor);

    List<Instructor> findAll();

    Instructor findById(int theId);

    void deleteById(int theId);

    List<Course> findCoursesById(int theId);

    Instructor findByIdJoinFetch(int theId);

    void update(Instructor instructor);
}