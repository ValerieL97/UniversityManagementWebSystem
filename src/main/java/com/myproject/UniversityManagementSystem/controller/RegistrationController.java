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

    private UserRepo userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepo userRepository, UserService userService,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("users",user);
        return "registration";
    }


    @RequestMapping(value="/registration", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("users")User user){

        if(!userService.addNewUser(user)) {
            return "redirect:/registration?success1";
        } else if(!user.getPasswordConf().equals(user.getPassword())) {
            return "redirect:/registration?success2";
        }

        user.setAdmin(false);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.STUDENT));
        userRepository.save(user);

        return "redirect:/login";
    }
}
