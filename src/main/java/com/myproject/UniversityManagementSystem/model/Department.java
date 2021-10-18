package com.myproject.UniversityManagementSystem.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;
    @Column(name="department_name", nullable = false, unique = true)
    private String departmentName;
    @Transient
    private int numCourses;
    @Transient
    private int numTeachers;
    @Transient
    private int numStudents;

    @OneToMany(mappedBy = "department",
              cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<Course> courses;

    @OneToMany(mappedBy = "department",
            cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<Teacher> teachers;

    public Department() {

    }

    public Department(String departmentName, int numCourses, int numTeachers, int numStudents) {
        this.departmentName = departmentName;
        this.numCourses = numCourses;
        this.numTeachers = numTeachers;
        this.numStudents = numStudents;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }


    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getNumCourses() {
        return numCourses;
    }

    public void setNumCourses(int numCourses) {
        this.numCourses = numCourses;
    }

    public int getNumTeachers() {
        return numTeachers;
    }

    public void setNumTeachers(int numTeachers) {
        this.numTeachers = numTeachers;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }


    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }


}
