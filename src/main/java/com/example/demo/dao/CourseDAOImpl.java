package com.example.demo.dao;

import com.example.demo.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CourseDAOImpl implements CourseDAO {
    private final EntityManager entityManager;

    @Autowired
    public CourseDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Course findById(int theId) {
        Course course = entityManager.find(Course.class, theId);

        // course.setReviews(null);
        // course.setStudents(null);

        return course;
    }

    @Override
    @Transactional
    public void update(Course course) {
        entityManager.merge(course);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        Course course = this.findById(theId);

        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void save(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findByIdJoinFetch(int theId) {
        TypedQuery<Course> theQuery = entityManager.createQuery(
                "select c from Course c JOIN FETCH c.reviews where c.id = :courseId", Course.class);

        theQuery.setParameter("courseId", theId);

        return theQuery.getSingleResult();
    }

    @Override
    public List<Course> findByIds(List<Integer> courseIds) {
        System.out.println("courseIds" + courseIds);

        if (courseIds == null || courseIds.isEmpty()) {
            return List.of();
        }

        TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c WHERE c.id IN :courseIds",
                                                             Course.class);

        query.setParameter("courseIds", courseIds);

        return query.getResultList();
    }
}
