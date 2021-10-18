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
public class UserService implements UserDetailsService {

    private UserRepo userRepo;
    private StudentService studentService;
    private PasswordEncoder passwordEncoder;

    public UserService(){

    }

    @Autowired
    public UserService(UserRepo userRepo, StudentService studentService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }

    public boolean addNewUser(User user) {
        User user1 = getUserByEmail(user.getEmail());
        User user2 = getUserByUsername(user.getUsername());

        if(user1 != null || user2 != null || checkUsername(user2) || checkStudentEmail(user1)) {
            return true;
        }

        return false;

    }

    public boolean checkUsername(User user) {
        List<Student> students = studentService.getAllStudents();
        for(Student student : students) {
            if (student.getCode().equals(user.getUsername())) {
                return true;
            }
        }

        return false;
    }

    public boolean checkStudentEmail(User user) {
        List<Student> students = studentService.getAllStudents();
        for(Student student : students) {
            if (student.getEmail().equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String code) {
        List<User> users = userRepo.findAll();
        User user = new User();

        for (User user1 : users) {
            if (user1.getUsername().equals(code)) {
                user = user1;
            }
        }

        return user;
    }

    public User getUserByEmail(String email) {
        List<User> users = userRepo.findAll();
        User user = new User();

        for (User user1 : users) {
            if (user1.getEmail().equals(email)) {
                user = user1;
            }
        }

        return user;
    }



}
