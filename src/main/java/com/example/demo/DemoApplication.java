package com.example.demo;

import com.example.demo.dao.StudentDAO;
import com.example.demo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
        return runner -> {
            // createStudent(studentDAO);
            // createMultipleStudents(studentDAO);
            // readStudent(studentDAO);
            // queryForStudents(studentDAO);
            // queryForStudentsByLastName(studentDAO);
            // updateStudent(studentDAO);
            // deleteStudent(studentDAO);
            // deleteAllStudents(studentDAO);
        };
    }

    private void deleteAllStudents(StudentDAO studentDAO) {
        int numRowsDeleted = studentDAO.deleteAll();
        System.out.println("Deleted row count: " + numRowsDeleted);
    }

    private void deleteStudent(StudentDAO studentDAO) {
        int studentId = 8;

        studentDAO.deleteById(studentId);
    }

    private void updateStudent(StudentDAO studentDAO) {
        int studentId = 8;

        Student myStudent = studentDAO.findById(studentId);

        myStudent.setLastName("Who de hell");

        studentDAO.update(myStudent);

        System.out.println(myStudent);
    }

    private void queryForStudentsByLastName(StudentDAO studentDAO) {
        List<Student> theStudents = studentDAO.findByLastName("Doe");

        for (Student tempStudent : theStudents) {
            System.out.println(tempStudent);
        }
    }

    private void queryForStudents(StudentDAO studentDAO) {
        List<Student> theStudents = studentDAO.findAll();

        for (Student tempStudent : theStudents) {
            System.out.println(tempStudent);
        }
    }

    private void readStudent(StudentDAO studentDAO) {
        Student tempStudent = new Student("John", "Doe", "test@gmail.com", 1);

        studentDAO.save(tempStudent);

        int theId = tempStudent.getId();

        Student student = studentDAO.findById(theId);
        System.out.println(student);
    }

    private void createMultipleStudents(StudentDAO studentDAO) {
        Student tempStudent1 = new Student("John1", "Doe1", "test1@gmail.com", 1);
        Student tempStudent2 = new Student("John2", "Doe2", "test2@gmail.com", 2);
        Student tempStudent3 = new Student("John3", "Doe3", "test3@gmail.com", 3);

        studentDAO.save(tempStudent1);
        studentDAO.save(tempStudent2);
        studentDAO.save(tempStudent3);
    }

    private void createStudent(StudentDAO studentDAO) {
        Student tempStudent = new Student("John", "Doe", "test@gmail.com", 1);

        studentDAO.save(tempStudent);

        System.out.println("Saved student. Generated id: " + tempStudent.getId());
    }
}
