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

    private final ClassRepo classRepo;

    public ClassService( ClassRepo classRepo) {
        this.classRepo = classRepo;
    }

    public Classes saveClass(Classes class1) {
        Classes class2 = classRepo.findByClassName(class1.getClassName());
        if(class2 == null){
            return classRepo.save(class1);
        }

        return null;
    }


    public Classes getClassById(Long classId) {
        return classRepo.findById(classId).get();
    }

    public void deleteClass(Long classId) {
        classRepo.deleteById(classId);
    }

    public Classes updateClassInfo(Classes classes, Long classId) {
        Classes class1 = getClassById(classId);
        class1.setTeacher(classes.getTeacher());
        class1.setId(classId);
        class1.setCourse(classes.getCourse());
        class1.setClassName(classes.getClassName());
        class1.setDuration(classes.getDuration());
        class1.setSemester(classes.getSemester());
        return classRepo.save(class1);
    }
}
