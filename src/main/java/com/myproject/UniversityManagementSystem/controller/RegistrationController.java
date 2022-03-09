package com.myproject.UniversityManagementSystem.controller;

import com.myproject.UniversityManagementSystem.model.Role;
import com.myproject.UniversityManagementSystem.model.User;
import com.myproject.UniversityManagementSystem.repo.UserRepo;
import com.myproject.UniversityManagementSystem.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@Controller
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "registration";
    }


    @RequestMapping(value="/registration", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user")User user){

        if(!user.getPasswordConf().equals(user.getPassword())) {
            return "redirect:/registration?success2";
        }else if(!userService.saveUser(user)) {
            return "redirect:/registration?success1";
        }

        return "redirect:/login";
    }
}
