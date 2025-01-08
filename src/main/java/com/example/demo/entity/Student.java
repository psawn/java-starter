package com.example.demo.entity;

import com.example.demo.validation.Course.CourseCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "student_id"),
               // inverseJoinColumns refer Course table
               inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    @Column(name = "country")
    private String country;

    @Column(name = "language")
    private String language;

    @Column(name = "systems")
    private List<String> systems;

    @Column(name = "free_passes")
    @NotNull(message = "is required")
    @Min(value = 1, message = "must be greater than or equal to 1")
    @Max(value = 10, message = "must be less than or equal to 10")
    private Integer freePasses;

    @Column(name = "postal_code")
    @Pattern(regexp = "^[a-zA-Z0-9]{5}", message = "only 5 chars/digits")
    private String postalCode;

    @Column(name = "course_code")
    @CourseCode(value = "JAVASCRIPT", message = "must start with JAVASCRIPT")
    private String courseCode;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, Integer freePasses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.freePasses = freePasses;
    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.freePasses = 1;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getFreePasses() {
        return freePasses;
    }

    public void setFreePasses(Integer freePasses) {
        this.freePasses = freePasses;
    }

    public List<String> getSystems() {
        return systems;
    }

    public void setSystems(List<String> systems) {
        this.systems = systems;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + '}';
    }

    public void addCourse(Course course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }

        if (!courses.contains(course)) {
            courses.add(course);
        }
    }
}