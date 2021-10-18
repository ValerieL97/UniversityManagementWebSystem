package com.myproject.UniversityManagementSystem.repo;

import com.myproject.UniversityManagementSystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepo extends JpaRepository<Department,Long> {

}
