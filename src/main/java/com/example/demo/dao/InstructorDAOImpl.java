package com.example.demo.dao;

import com.example.demo.entity.Course;
import com.example.demo.entity.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class InstructorDAOImpl implements InstructorDAO {
    private final EntityManager entityManager;

    @Autowired
    public InstructorDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public List<Instructor> findAll() {
        TypedQuery<Instructor> theQuery = entityManager.createQuery("FROM Instructor", Instructor.class);

        return theQuery.getResultList();
    }

    @Override
    public Instructor findById(int theId) {
        return entityManager.find(Instructor.class, theId);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        Instructor instructor = this.findById(theId);

        List<Course> courses = instructor.getCourses();

        // break association between instructor and courses
        for (Course course : courses) {
            course.setInstructor(null);
        }

        entityManager.remove(instructor);
    }

    @Override
    public List<Course> findCoursesById(int theId) {
        TypedQuery<Course> theQuery = entityManager.createQuery("FROM Course where instructor.id = :instructorId",
                                                                Course.class);

        theQuery.setParameter("instructorId", theId);

        return theQuery.getResultList();
    }

    @Override
    public Instructor findByIdJoinFetch(int theId) {
        // add JOIN FETCH i.instructorDetail is meaningless
        TypedQuery<Instructor> theQuery = entityManager.createQuery(
                "select i from Instructor i JOIN FETCH i.courses JOIN FETCH i.instructorDetail where i.id = :instructorId",
                Instructor.class);

        theQuery.setParameter("instructorId", theId);

        return theQuery.getSingleResult();
    }

    @Override
    @Transactional
    public void update(Instructor instructor) {
        entityManager.merge(instructor);
    }
}
