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

   private DepartmentRepo departmentRepo;
   private CourseService courseService;
   private TeacherService teacherService;

    public DepartmentService(DepartmentRepo departmentRepo,
                             CourseService courseService,
                             TeacherService teacherService) {
        this.departmentRepo = departmentRepo;
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    public Department getDepartmentById(Long id) {

        return departmentRepo.getById(id);
    }
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }
    public Department saveDepartment(Department department){

        return departmentRepo.save(department);
    }
    public Department updateDepartment(Department department){

        return departmentRepo.save(department);
    }
    public void deleteDepartment(Long id) {

        departmentRepo.deleteById(id);
    }

    public Department getDepartmentByName(String departmentName) {
        List<Department> departments = getAllDepartments();
        Department department = null;

        for(Department dep : departments) {
            if(dep.getDepartmentName() == departmentName) {
                department =  dep;
            }
        }

        return department;

    }



    public int countNumberCourses(Long id) {
        Set<Course> courses = courseService.findByDepartment(getDepartmentById(id));
        return courses.size();
    }

    public int countNumberTeachers(Long id) {
        Set<Teacher> teachers = teacherService.findByDepartment(getDepartmentById(id));
        return teachers.size();
    }

    public int countNumberStudents(Long id) {
        Set<Course> courses = courseService.findByDepartment(getDepartmentById(id));
        int numStudents = 0;

        for(Course course: courses) {
            numStudents = numStudents + courseService.countNumStudents(course.getCourseId());
        }

        return numStudents;

    }

    public boolean existingDepartment(String name) {
        List<Department> departments = getAllDepartments();
        for (Department department1 : departments) {
            if (department1.getDepartmentName().equals(name)) {
                return true;
            }
        }
        return false;
    }


}
