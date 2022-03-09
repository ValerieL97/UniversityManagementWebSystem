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

    private final ResultRepo resultRepo;

    public ResultService(ResultRepo resultRepo) {
        this.resultRepo = resultRepo;
    }

    public List<Result> findByClass(Classes mainClass) {
        List<Result> resultsByClass = resultRepo.findByClasses(mainClass);
        for(Result result : resultRepo.findAll()) {
            if(result.getClasses()!= null) {
                if (result.getClasses().equals(mainClass)) {
                    resultsByClass.add(result);
                }
            }
        }

        return resultsByClass;

    }

    public boolean saveResult(Result result) {
       List<Result> resultsByStudent = resultRepo.findByStudent(result.getStudent());

       for(Result result1 : resultsByStudent) {
           if(result1.getClasses().equals(result.getClasses())) {
               return false;
           }
       }

       resultRepo.save(result);
       return true;
    }

    public void deleteResult(Long resultId) {
        resultRepo.deleteById(resultId);
    }

    public List<Result> findResultsByStudent(Student student) {
        return resultRepo.findByStudent(student);
    }
}
