package com.myproject.UniversityManagementSystem.service;

import com.myproject.UniversityManagementSystem.model.*;
import com.myproject.UniversityManagementSystem.repo.CourseRepo;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseService {

    private final CourseRepo courseRepo;

    public CourseService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }


    public Course saveCourse(Course course) {
        Course courseByName = courseRepo.findByName(course.getName());
        if(courseByName == null) {
            return courseRepo.save(course);
        }
        return null;
    }

    public Course findCourseById(Long courseId) {
        return courseRepo.findById(courseId).get();
    }

    public void deleteCourse(Long courseId) {
        courseRepo.deleteById(courseId);
    }

    public Course updateCourseInfo(Long courseId, Course course) {
        Course course1 = findCourseById(courseId);
        course1.setId(courseId);
        course1.setDepartment(course.getDepartment());
        course1.setName(course.getName());
        course1.setDuration(course.getDuration());
        course1.setNumStudents(course.getNumStudents());
        course1.setStudents(course.getStudents());
        course1.setClasses(course.getClasses());
        return courseRepo.save(course1);
    }




}
