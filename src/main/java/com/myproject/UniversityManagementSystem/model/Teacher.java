package com.myproject.UniversityManagementSystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="teacherName",nullable = false)
    private String teacherName;

    @Column(name="dateBirth",nullable = false)
    private String dateBirth;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name="phoneNumber",nullable = false)
    private String phoneNumber;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="num_classes", nullable = false, columnDefinition = "integer default 0")
    private int numClasses;


    @OneToMany(mappedBy = "teacher",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Classes> classes;


    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="departmentId", nullable = false)
    private Department department;

    public Teacher() {
    }

    public Teacher(String teacherName, String address,
                   String dateBirth, String email, String phoneNumber, int numClasses) {
        this.teacherName = teacherName;
        this.address = address;
        this.dateBirth = dateBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.numClasses = numClasses;
    }

    public Long getId() {
        return id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Classes> getClasses() {
        return classes;
    }

    public void setClasses(Set<Classes> classes) {
        this.classes = classes;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getNumClasses() {
        return numClasses;
    }

    public void setNumClasses(int numClasses) {
        this.numClasses = numClasses;
    }
}
