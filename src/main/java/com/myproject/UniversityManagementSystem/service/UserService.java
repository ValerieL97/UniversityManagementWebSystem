package com.myproject.UniversityManagementSystem.service;

import com.myproject.UniversityManagementSystem.model.Student;
import com.myproject.UniversityManagementSystem.model.User;
import com.myproject.UniversityManagementSystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepo userRepo;
    private StudentService studentService;
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepo userRepo, StudentService studentService,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveUser(User user) {
        User userByEmail = userRepo.findByEmail(user.getEmail());
        User userByCode = userRepo.findByUsername(user.getUsername());
        Student studentByCode = studentService.getStudentByCode(user.getUsername());

        if(userByEmail == null && userByCode == null && studentByCode != null) {
            user.setAdmin(false);
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.ADMIN));
            userRepo.save(user);
            return true;
        }

        return false;

    }


    public User getUserByUsername(String code) {
        return userRepo.findByUsername(code);
    }

}
