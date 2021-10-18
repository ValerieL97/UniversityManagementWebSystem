package com.myproject.UniversityManagementSystem.repo;

import com.myproject.UniversityManagementSystem.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepo extends JpaRepository<Classes,Long> {
}
