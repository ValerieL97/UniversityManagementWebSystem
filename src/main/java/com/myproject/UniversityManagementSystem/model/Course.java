package com.myproject.UniversityManagementSystem.model;

import com.myproject.UniversityManagementSystem.service.DepartmentService;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    @Column(name="course_name", nullable = false, unique = true)
    private String name;
    @Column(name="course_duration", nullable = false)
    private String duration;
    @Transient
    private int numClasses;
    @Column(name="num_classes", nullable = false, columnDefinition = "integer default 0")
    private int numStudents;


    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="departmentId", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "course",
            cascade = {CascadeType.ALL})
    private Set<Classes> classes;

    @OneToMany(mappedBy = "course",
            cascade = {CascadeType.ALL})
    private Set<Student> students;

    public Course() {

    }

    public Course(String name, String duration, int numClasses, int numStudents) {
        this.name = name;
        this.duration = duration;
        this.numClasses = numClasses;
        this.numStudents = numStudents;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getNumClasses() {
        return numClasses;
    }

    public void setNumClasses(int numClasses) {
        this.numClasses = numClasses;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    public Set<Classes> getClasses() {
        return classes;
    }

    public void setClasses(Set<Classes> classes) {
        this.classes = classes;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }


}
