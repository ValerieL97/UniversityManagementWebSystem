package com.myproject.UniversityManagementSystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="results")

public class Result {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;


    @ManyToOne
    @JoinColumn(name = "classId")
    private Classes classes;

    @Column(name="result")
    private int result;

    public Result() {

    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }


}
