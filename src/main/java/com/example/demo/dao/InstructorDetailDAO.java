package com.example.demo.dao;

import com.example.demo.entity.InstructorDetail;

public interface InstructorDetailDAO {
    InstructorDetail findById(int theId);

    void deleteById(int theId);
}
