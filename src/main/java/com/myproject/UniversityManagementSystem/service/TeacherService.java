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
    private TeacherRepo teacherRepo;
    private ClassService classService;

    public TeacherService(TeacherRepo teacherRepo, ClassService classService) {
        this.teacherRepo = teacherRepo;
        this.classService = classService;
    }

    public Teacher findTeacherById(Long id){
        return teacherRepo.getById(id);
    }

    public Set<Teacher> findByDepartment(Department department) {
        Set<Teacher> depTeachers = new HashSet<>();
        for(Teacher teacher : teacherRepo.findAll()) {
            if(teacher.getDepartment().equals(department)) {
                depTeachers.add(teacher);
            }
        }

        return depTeachers;
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepo.save(teacher);
    }

    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepo.save(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepo.deleteById(id);
    }

    public int numberClasses(Teacher teacher) {
        List<Classes> classes = classService.getAllClasses();
        int numClasses = 0;

        for(Classes class1 : classes) {
            if(class1.getTeacher().equals(teacher)) {
                numClasses = numClasses + 1;
            }
        }

        return numClasses;
    }

    public Set<Teacher> findTeacherByDepartment(Department department) {
        Set<Teacher> depTeachers = new HashSet<>();
        for(Teacher teacher : teacherRepo.findAll()) {
            if(teacher.getDepartment().equals(department)) {
                depTeachers.add(teacher);
            }
        }

        return depTeachers;
    }

    public boolean existingTeacher(Teacher teacher) {
        List<Teacher> teachers = teacherRepo.findAll();
        for (Teacher teacher1 : teachers) {
            if (teacher1.getEmail().equals(teacher.getEmail()) ||
                    (teacher1.getEmail().equals(teacher.getEmail()) &&
                            teacher1.getTeacherName().equals(teacher.getTeacherName()))) {
                return true;
            }
        }
        return false;
    }

}
