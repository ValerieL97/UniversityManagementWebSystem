package com.myproject.UniversityManagementSystem.service;


import com.myproject.UniversityManagementSystem.model.Classes;
import com.myproject.UniversityManagementSystem.model.Course;
import com.myproject.UniversityManagementSystem.model.Student;
import com.myproject.UniversityManagementSystem.repo.ClassRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClassService {

    private ClassRepo classRepo;

    public ClassService(ClassRepo classRepo) {
        this.classRepo = classRepo;
    }

    public List<Classes> getAllClasses() {
        return classRepo.findAll();
    }

    public Classes saveClass(Classes class1){
        return classRepo.save(class1);
    }

    public Classes updateClass(Classes class1){
        return classRepo.save(class1);
    }

    public Classes getClassById(Long id) {
        return classRepo.getById(id);
    }
    public void deleteClassById(Long id){
        classRepo.deleteById(id);
    }

    public Set<Classes> findByCourse(Course course) {
        Set<Classes> courseClasses = new HashSet<>();
        for(Classes class1 : classRepo.findAll()) {
            if(class1.getCourse().equals(course)) {
               courseClasses.add(class1);
            }
        }

        return courseClasses;
    }

    public boolean existingClass(Classes classes) {
        List<Classes> classesList = getAllClasses();
        for (Classes class1: classesList) {
            if (class1.getClassName().equals(classes.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
