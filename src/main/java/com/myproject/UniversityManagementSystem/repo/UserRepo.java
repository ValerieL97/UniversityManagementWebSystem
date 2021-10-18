package com.myproject.UniversityManagementSystem.repo;

import com.myproject.UniversityManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
}
