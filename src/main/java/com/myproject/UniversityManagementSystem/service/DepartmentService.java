package com.myproject.UniversityManagementSystem.service;

//import com.myproject.UniversityManagementSystem.model.Course;
import com.myproject.UniversityManagementSystem.model.Course;
import com.myproject.UniversityManagementSystem.model.Department;
//import com.myproject.UniversityManagementSystem.model.Teacher;
import com.myproject.UniversityManagementSystem.model.Student;
import com.myproject.UniversityManagementSystem.model.Teacher;
import com.myproject.UniversityManagementSystem.repo.DepartmentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class DepartmentService {

       private final DepartmentRepo departmentRepo;

    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();

    }

    public int calculateNumberOfStudents(Department department) {
        List<Course> courses = department.getCourses();
        int studentsNum = 0;

        for(Course course : courses) {
            studentsNum = studentsNum + course.getStudents().size();
        }

        return studentsNum;
    }

    public boolean existingDepartment(String departmentName) {
        Department department = departmentRepo.findByDepartmentName(departmentName);
        if(department != null) {
            return true;
        }
        return false;
    }

    public Department saveDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepo.findById(id).get();
    }

    public Department updateDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public void deleteDepartment(Long id) {
        departmentRepo.deleteById(id);
    }


}
