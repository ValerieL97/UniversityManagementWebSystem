package com.myproject.UniversityManagementSystem.repo;

import com.myproject.UniversityManagementSystem.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeacherRepo extends JpaRepository<Teacher,Long> {

}
