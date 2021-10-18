package com.myproject.UniversityManagementSystem.model;

import com.myproject.UniversityManagementSystem.model.Course;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    @Column(name="student_name", nullable = false)
    private String studentName;

    @Column(name="date_Birth",nullable = false)
    private String dateBirth;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name="phone_Number",nullable = false)
    private String phoneNumber;


    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="name")
    private Course course;


    @Column(name="year",nullable = false)
    private String year;


    @OneToMany(mappedBy = "student",cascade = {CascadeType.ALL})
    private Set<Result> results;

    @Column(name="code",nullable = false,unique = true)
    private String code;

    public Student() {
    }

    public Student(String studentName, String dateBirth, String email,
                   String phoneNumber,
                   Course course, String year) {
        this.studentName = studentName;
        this.dateBirth = dateBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
        this.year = year;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
