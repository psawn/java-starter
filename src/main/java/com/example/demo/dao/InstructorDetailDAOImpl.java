package com.example.demo.dao;

import com.example.demo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class InstructorDetailDAOImpl implements InstructorDetailDAO {
    private final EntityManager entityManager;

    @Autowired
    public InstructorDetailDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public InstructorDetail findById(int theId) {
        return entityManager.find(InstructorDetail.class, theId);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class, theId);

        instructorDetail.getInstructor().setInstructorDetail(null);

        entityManager.remove(instructorDetail);
    }
}