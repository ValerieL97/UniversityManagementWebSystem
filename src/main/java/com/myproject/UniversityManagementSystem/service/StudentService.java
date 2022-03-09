package com.myproject.UniversityManagementSystem.service;

import com.myproject.UniversityManagementSystem.model.Course;
import com.myproject.UniversityManagementSystem.model.Student;
import com.myproject.UniversityManagementSystem.model.Teacher;
import com.myproject.UniversityManagementSystem.repo.StudentRepo;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public String generateCodeForLogin() {

        Random random = new Random();
        boolean isTrue = true;
        String code = "";
        do {
            int id = random.nextInt(999999);
            code = String.format("%06d", id);
            if(verifyExistingValues(code)) {
                isTrue = false;
            }

        } while(isTrue);

        return code;
    }

    private boolean verifyExistingValues(String string) {
        List<Student> students = studentRepo.findAll();
        for(Student student : students) {
            if(student.getCode().equals(string)) {
                return false;
            }
        }

        return true;
    }


    public Student saveStudent(Student student) {
        Student studentByEmail = studentRepo.findByEmail(student.getEmail());
        Student studentByName = studentRepo.findByStudentName(student.getStudentName());
        if(studentByEmail != null || studentByName != null) {
            return null;
        }
        student.setCode(generateCodeForLogin());
        return studentRepo.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepo.getById(id);
    }

    public void deleteStudent(Long studentId) {
        studentRepo.deleteById(studentId);
    }

    public Student getStudentByCode(String code) {
        return studentRepo.findByCode(code);
    }

    public Student updateStudentInfo(Long studentId, Student student) {
        Student student1 = getStudentById(studentId);
        student1.setId(studentId);
        student1.setStudentName(student.getStudentName());
        student1.setDateBirth(student.getDateBirth());
        student1.setCourse(student.getCourse());
        student1.setCode(getStudentById(studentId).getCode());
        student1.setEmail(student.getEmail());
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setYear(student.getYear());
        return studentRepo.save(student1);
    }
}
