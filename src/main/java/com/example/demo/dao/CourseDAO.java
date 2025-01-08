package com.example.demo.dao;

import com.example.demo.entity.Course;

import java.util.List;

public interface CourseDAO {
    Course findById(int theId);

    void update(Course course);

    void deleteById(int theId);

    void save(Course course);

    Course findByIdJoinFetch(int theId);

    List<Course> findByIds(List<Integer> courseIds);
}