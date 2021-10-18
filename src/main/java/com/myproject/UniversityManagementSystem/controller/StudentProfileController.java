package com.myproject.UniversityManagementSystem.controller;


import com.myproject.UniversityManagementSystem.model.Student;
import com.myproject.UniversityManagementSystem.service.ResultService;
import com.myproject.UniversityManagementSystem.service.StudentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class StudentProfileController {

    private StudentService studentService;
    private ResultService resultService;

    public StudentProfileController(ResultService resultService,StudentService studentService) {
        this.studentService = studentService;
        this.resultService = resultService;
    }

    @GetMapping("/profile")
    public String listStudentsData(Model model) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.getStudentByCode(principal.getName());
        model.addAttribute("students", student);
        model.addAttribute("results", resultService.findByStudent(student));
        return "studentProfile";
    }
}

