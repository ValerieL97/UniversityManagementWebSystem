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

    private StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Student saveStudent(Student student) {
        return studentRepo.save(student);
    }

    public Student updateStudent(Student student) {
        return studentRepo.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepo.getById(id);
    }

    public void deleteStudentById(Long id) {
        studentRepo.deleteById(id);
    }

    public Set<Student> findByCourse(Course course) {
        Set<Student> courseStudents = new HashSet<>();
        for(Student student1 : studentRepo.findAll()) {
            if(student1.getCourse().equals(course)) {
                courseStudents.add(student1);
            }
        }
        return courseStudents;
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

    public boolean verifyExistingValues(String string) {
        List<Student> students = studentRepo.findAll();
        for(Student student : students) {
            if(student.getCode().equals(string)) {
                return false;
            }
        }

        return true;
    }

    public Student getStudentByCode(String code) {
        List<Student> students = studentRepo.findAll();
        Student student1 = new Student();

        for(Student student : students) {
            if(student.getCode().equals(code)){
                student1 = student;
            }
        }

        return student1;

    }

    public boolean existingStudent(Student student) {
        List<Student> students = getAllStudents();
        for (Student student1 : students) {
            if (student1.getEmail().equals(student.getEmail()) ||
                    (student1.getEmail().equals(student.getEmail()) &&
                            student1.getStudentName().equals(student.getStudentName()))) {
                return true;
            }
        }
        return false;
    }
}
