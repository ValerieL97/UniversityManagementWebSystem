package com.myproject.UniversityManagementSystem.repo;

import com.myproject.UniversityManagementSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepo extends JpaRepository<Student,Long> {
}
