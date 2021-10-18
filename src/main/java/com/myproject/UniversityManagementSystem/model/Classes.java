package com.myproject.UniversityManagementSystem.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="classes")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long classId;
    @Column(name="class_name", nullable = false, unique = true)
    String className;
    @Column(name="class_duration", nullable = false)
    String duration;

    @Column(name="begin_semester", nullable = false)
    String semester;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="courseName")
    private Course course;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="teacherName")
    private Teacher teacher;


    @OneToMany(mappedBy = "classes",cascade = {CascadeType.ALL})
    private Set<Result> results;


    public Classes() {
    }

    public Classes(String className, String duration, String semester) {
        this.className = className;
        this.duration = duration;
        this.semester = semester;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }
}
