package com.myproject.UniversityManagementSystem.repo;

import com.myproject.UniversityManagementSystem.model.Course;
import com.myproject.UniversityManagementSystem.model.Department;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Set;

public interface CourseRepo extends JpaRepository<Course,Long> {

}
