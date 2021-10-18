package com.myproject.UniversityManagementSystem.service;

import com.myproject.UniversityManagementSystem.model.Classes;
import com.myproject.UniversityManagementSystem.model.Course;
import com.myproject.UniversityManagementSystem.model.Result;
import com.myproject.UniversityManagementSystem.model.Student;
import com.myproject.UniversityManagementSystem.repo.ResultRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResultService {

    private ResultRepo resultRepo;
    private StudentService studentService;

    public ResultService(ResultRepo resultRepo, StudentService studentService) {

        this.resultRepo = resultRepo;
        this.studentService = studentService;
    }

    public Set<Result> findByClass(Classes class1) {
        Set<Result> resultsClass = new HashSet<>();
        for(Result result : resultRepo.findAll()) {
            if(result.getClasses()!= null) {
                if (result.getClasses().equals(class1)) {
                    resultsClass.add(result);
                }
            }
        }
        return resultsClass;
    }

    public Set<Result> findByStudent(Student student) {
        Set<Result> resultsStudent = new HashSet<>();
        for(Result result : resultRepo.findAll()) {
            if(result.getStudent().equals(student)) {
                resultsStudent.add(result);
            }
        }
        return resultsStudent;
    }

    public Result saveResult(Result result) {

        return resultRepo.save(result);
    }

    public Result updateResult(Result result) {

        return resultRepo.save(result);
    }

    public Result getResultById(Long id) {

        return resultRepo.getById(id);
    }

    public void deleteResult(Long id) {
        resultRepo.deleteById(id);
    }

    public boolean existingResult(Long studentId) {
        List<Result> results = resultRepo.findAll();
        for (Result result1 : results) {
            if (result1.getStudent().equals(studentService.getStudentById(studentId))) {
                return true;
            }
        }
        return false;
    }
}
