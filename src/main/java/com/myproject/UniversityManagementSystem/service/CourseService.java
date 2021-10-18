package com.myproject.UniversityManagementSystem.service;

import com.myproject.UniversityManagementSystem.model.*;
import com.myproject.UniversityManagementSystem.repo.CourseRepo;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseService {

    private CourseRepo courseRepo;
    private ClassService classService;
    private StudentService studentService;

    public CourseService(CourseRepo courseRepo, ClassService classService, StudentService studentService) {
        this.courseRepo = courseRepo;
        this.classService = classService;
        this.studentService = studentService;
    }

    public Course findCourseById(Long id){
        return courseRepo.getById(id);
    }

    public Set<Course> findByDepartment(Department department) {
        Set<Course> depCourses = new HashSet<>();
        for(Course course : courseRepo.findAll()) {
            if(course.getDepartment().equals(department)) {
                depCourses.add(course);
            }
        }

        return depCourses;
    }

    public Course saveCourse(Course course) {
        return courseRepo.save(course);
    }

    public Course updateCourse(Course course) {
        return courseRepo.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepo.deleteById(id);
    }

    public List<Course> getAllCourses(){
        return courseRepo.findAll();
    }

    public int countNumClasses(Long id) {
        Set<Classes> classes = classService.findByCourse(findCourseById(id));
        return classes.size();
    }
    
    public int countNumStudents(Long id) {
        Set<Student> students = studentService.findByCourse(findCourseById(id));
        return students.size();
    }

    public boolean existingCourse(Course course) {
        List<Course> courses = getAllCourses();
        for (Course course1 : courses) {
            if (course1.equals(course)) {
                return true;
            }
        }
        return false;
    }




}
