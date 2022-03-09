package com.myproject.UniversityManagementSystem.service;

import com.myproject.UniversityManagementSystem.model.Classes;
import com.myproject.UniversityManagementSystem.model.Course;
import com.myproject.UniversityManagementSystem.model.Department;
import com.myproject.UniversityManagementSystem.model.Teacher;
import com.myproject.UniversityManagementSystem.repo.TeacherRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeacherService {
    private final TeacherRepo teacherRepo;

    public TeacherService(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }


    public Teacher saveTeacher(Teacher teacher) {
        Teacher teacher1 = teacherRepo.findByEmail(teacher.getEmail());

        if(teacher1 == null) {
            return teacherRepo.save(teacher);
        }

        return null;
    }

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepo.findById(teacherId).get();
    }

    public void deleteTeacher(Long teacherId) {
        teacherRepo.deleteById(teacherId);
    }

    public Teacher updateTeacherInfo(Teacher teacher, Long teacherId) {
        Teacher teacher1 = findTeacherById(teacherId);
        teacher1.setId(teacherId);
        teacher1.setTeacherName(teacher.getTeacherName());
        teacher1.setAddress(teacher.getAddress());
        teacher1.setEmail(teacher.getEmail());
        teacher1.setDateBirth(teacher.getDateBirth());
        teacher1.setDepartment(teacher.getDepartment());
        teacher1.setPhoneNumber(teacher.getPhoneNumber());
        teacher1.setClasses(teacher.getClasses());
        return teacherRepo.save(teacher1);
    }

}
