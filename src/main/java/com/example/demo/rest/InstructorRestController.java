package com.example.demo.rest;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.validation.Course.*;
import com.example.demo.validation.Instructor.*;
import com.example.demo.validation.Review.*;
import com.example.demo.validation.Student.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/instructors")
public class InstructorRestController {
    private final InstructorDAO instructorDAO;
    private final InstructorDetailDAO instructorDetailDAO;
    private final CourseDAO courseDAO;
    private final StudentDAO studentDAO;

    @Autowired
    public InstructorRestController(InstructorDAO theInstructorDAO, InstructorDetailDAO theInstructorDetailDAO,
                                    CourseDAO theCourseDAO, StudentDAO theStudentDAO) {
        this.instructorDAO = theInstructorDAO;
        this.instructorDetailDAO = theInstructorDetailDAO;
        this.courseDAO = theCourseDAO;
        this.studentDAO = theStudentDAO;
    }

    private static CourseDTO transformToCourseDTO(Course courseData) {
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId(courseData.getId());
        courseDTO.setTitle(courseData.getTitle());

        List<Student> students = courseData.getStudents();
        List<Review> reviews = courseData.getReviews();

        if (students != null && !students.isEmpty()) {
            List<StudentDTO> studentDTOs = students.stream().map(student -> {
                StudentDTO studentDTO = new StudentDTO();

                studentDTO.setId(student.getId());
                studentDTO.setEmail(student.getEmail());
                studentDTO.setFirstName(student.getFirstName());
                studentDTO.setLastName(student.getLastName());

                return studentDTO;
            }).toList();

            courseDTO.setStudents(studentDTOs);
        }

        if (reviews != null && !reviews.isEmpty()) {
            List<ReviewDTO> reviewDTOs = reviews.stream().map(review -> {
                ReviewDTO tempReviewDTO = new ReviewDTO();

                tempReviewDTO.setId(review.getId());
                tempReviewDTO.setComment(review.getComment());

                return tempReviewDTO;
            }).toList();

            courseDTO.setReviews(reviewDTOs);
        }

        return courseDTO;
    }

    private static StudentDTO transformToStudentDTO(Student studentData) {
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId(studentData.getId());
        studentDTO.setFirstName(studentData.getFirstName());
        studentDTO.setLastName(studentData.getLastName());
        studentDTO.setEmail(studentData.getEmail());

        List<Course> courses = studentData.getCourses();

        if (courses != null && !courses.isEmpty()) {
            List<CourseDTO> courseDTOs = courses.stream().map(course -> {
                CourseDTO tempCourseDTO = new CourseDTO();

                tempCourseDTO.setId(course.getId());
                tempCourseDTO.setTitle(course.getTitle());

                return tempCourseDTO;
            }).toList();

            studentDTO.setCourses(courseDTOs);
        }

        return studentDTO;
    }

    @PostMapping
    public void create(@RequestBody CreateInstructorRequest createInstructorRequest) {
        Instructor tempInstructor = new Instructor(createInstructorRequest.getFirstName(),
                                                   createInstructorRequest.getLastName(),
                                                   createInstructorRequest.getEmail());

        InstructorDetail tempInstructorDetail = new InstructorDetail(
                createInstructorRequest.getInstructorDetail().getYoutubeChannel(),
                createInstructorRequest.getInstructorDetail().getHobby());

        List<CourseRequest> courseRequests = createInstructorRequest.getCourses();

        tempInstructor.setInstructorDetail(tempInstructorDetail);

        if (courseRequests != null && !courseRequests.isEmpty()) {
            for (CourseRequest courseRequest : courseRequests) {
                Course course = new Course(courseRequest.getTitle());
                tempInstructor.addCourse(course);
            }
        }

        instructorDAO.save(tempInstructor);
    }

    @GetMapping
    public List<Instructor> getInstructor() {
        return instructorDAO.findAll();
    }

    @GetMapping("/{instructorId}")
    public Instructor getById(@PathVariable int instructorId) {
        Instructor instructor = instructorDAO.findById(instructorId);

        if (instructor == null) {
            throw new RuntimeException("Instructor id not found - " + instructorId);
        }

        return instructor;
    }

    @GetMapping("/{instructorId}/detail")
    public InstructorDetail getDetailById(@PathVariable int instructorId) {
        Instructor instructor = instructorDAO.findById(instructorId);

        if (instructor == null) {
            throw new RuntimeException("Instructor id not found - " + instructorId);
        }

        InstructorDetail instructorDetail = instructor.getInstructorDetail();

        if (instructorDetail == null) {
            throw new RuntimeException("Instructor id does not have detail - " + instructorId);
        }

        return instructorDetail;
    }

    @GetMapping("/{instructorId}/courses")
    public List<CourseDTO> getCoursesById(@PathVariable int instructorId) {
        Instructor instructor = instructorDAO.findById(instructorId);

        if (instructor == null) {
            throw new RuntimeException("Instructor id not found - " + instructorId);
        }

        List<Course> courses = instructorDAO.findCoursesById(instructorId);

        List<CourseDTO> courseDTOs = courses.stream().map(course -> transformToCourseDTO(course)).toList();

        return courseDTOs;
    }

    @PostMapping("/{instructorId}/courses")
    public void addCoursesToInstructor(@PathVariable int instructorId,
                                       @RequestBody CreateCourseRequest createCourseRequest) {
        Instructor instructor = instructorDAO.findById(instructorId);

        if (instructor == null) {
            throw new RuntimeException("Instructor id not found - " + instructorId);
        }

        List<CourseRequest> courseRequests = createCourseRequest.getCourses();

        if (courseRequests != null && !courseRequests.isEmpty()) {
            for (CourseRequest courseRequest : courseRequests) {
                Course course = new Course(courseRequest.getTitle());
                instructor.addCourse(course);
            }
        }

        instructorDAO.update(instructor);
    }

    @GetMapping("/{instructorId}/join-fetch")
    public Instructor getByIdJoinFetch(@PathVariable int instructorId) {
        return instructorDAO.findByIdJoinFetch(instructorId);
    }

    @PutMapping("/{instructorId}")
    public void update(@PathVariable int instructorId, @RequestBody UpdateInstructorRequest updateInstructorRequest) {
        Instructor instructor = instructorDAO.findById(instructorId);

        if (instructor == null) {
            throw new RuntimeException("Instructor id not found - " + instructorId);
        }

         // Optional.ofNullable(updateInstructorRequest.getFirstName()).ifPresent(value -> instructor.setFirstName(value));
         // Optional.ofNullable(updateInstructorRequest.getLastName()).ifPresent(value -> instructor.setLastName(value));

        Optional.ofNullable(updateInstructorRequest.getFirstName()).ifPresent(instructor::setFirstName);
        Optional.ofNullable(updateInstructorRequest.getLastName()).ifPresent(instructor::setLastName);

        instructorDAO.update(instructor);
    }

    @DeleteMapping("/{instructorId}")
    public void delete(@PathVariable int instructorId) {
        Instructor instructor = instructorDAO.findById(instructorId);

        if (instructor == null) {
            throw new RuntimeException("Instructor id not found - " + instructorId);
        }

        instructorDAO.deleteById(instructorId);
    }

    @DeleteMapping("/detail/{detailId}")
    public void deleteDetail(@PathVariable int detailId) {
        InstructorDetail instructorDetail = instructorDetailDAO.findById(detailId);

        if (instructorDetail == null) {
            throw new RuntimeException("Instructor detail id not found - " + detailId);
        }

        instructorDetailDAO.deleteById(detailId);
    }

    @PostMapping("/courses/{courseId}/reviews")
    public void addReviewsToCourse(@PathVariable int courseId, @RequestBody CreateReviewRequest createReviewRequest) {
        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new RuntimeException("Course id not found - " + courseId);
        }

        List<ReviewRequest> reviewRequests = createReviewRequest.getReviews();

        if (reviewRequests != null && !reviewRequests.isEmpty()) {
            for (ReviewRequest reviewRequest : reviewRequests) {
                Review review = new Review(reviewRequest.getComment());
                course.addReview(review);
            }
        }

        courseDAO.update(course);
    }

    @PostMapping("/courses/{courseId}/students")
    public void addStudentsToCourse(@PathVariable int courseId,
                                    @RequestBody CreateStudentRequest createStudentRequest) {
        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new RuntimeException("Course id not found - " + courseId);
        }

        List<StudentRequest> studentRequests = createStudentRequest.getStudents();

        if (studentRequests != null && !studentRequests.isEmpty()) {
            for (StudentRequest studentRequest : studentRequests) {
                Student student = new Student(studentRequest.getFirstName(), studentRequest.getLastName(),
                                              studentRequest.getEmail());

                course.addStudent(student);
            }
        }

        courseDAO.update(course);
    }

    @GetMapping("/courses/{courseId}")
    public CourseDTO getCoursesByCourseId(@PathVariable int courseId) {
        Course course = courseDAO.findByIdJoinFetch(courseId);
        // Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new RuntimeException("Course id not found - " + courseId);
        }

        return transformToCourseDTO(course);
    }

    @PutMapping("/courses/{courseId}")
    public void updateCourse(@PathVariable int courseId, @RequestBody UpdateCourseRequest updateCourseRequest) {
        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new RuntimeException("Course id not found - " + courseId);
        }

        Optional.ofNullable(updateCourseRequest.getTitle()).ifPresent(course::setTitle);

        courseDAO.update(course);
    }

    @DeleteMapping("/courses/{courseId}")
    public void deleteCourse(@PathVariable int courseId) {
        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new RuntimeException("Course id not found - " + courseId);
        }

        courseDAO.deleteById(courseId);
    }

    @GetMapping("/students/{studentId}")
    public StudentDTO getStudentByStudentId(@PathVariable int studentId) {
        Student student = studentDAO.findById(studentId);

        if (student == null) {
            throw new RuntimeException("Student id not found - " + studentId);
        }

        return transformToStudentDTO(student);
    }

    @PostMapping("/students/{studentId}/courses")
    public void addCoursesToStudent(@PathVariable int studentId, @RequestBody CourseIdsRequest courseIdsRequest) {
        Student student = studentDAO.findById(studentId);

        if (student == null) {
            throw new RuntimeException("Student id not found - " + studentId);
        }

        List<Course> courses = courseDAO.findByIds(courseIdsRequest.getCourseIds());

        System.out.println("courses: " + courses);

        if (courses != null && !courses.isEmpty()) {
            for (Course course : courses) {
                student.addCourse(course);
            }
        }

        studentDAO.update(student);
    }

    @DeleteMapping("/students/{studentId}")
    public void deleteStudentByStudentId(@PathVariable int studentId) {
        Student student = studentDAO.findById(studentId);

        if (student == null) {
            throw new RuntimeException("Student id not found - " + studentId);
        }

        studentDAO.deleteById(studentId);
    }
}