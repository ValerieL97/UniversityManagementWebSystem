package com.myproject.UniversityManagementSystem.controller;


import com.myproject.UniversityManagementSystem.model.Department;
import com.myproject.UniversityManagementSystem.service.DepartmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public String listDepartments(Model model) {
        List<Department> departmentList = departmentService.getAllDepartments();

        for(Department department : departmentList) {
            department.setNumStudents(departmentService.calculateNumberOfStudents(department));
            department.setNumTeachers(department.getTeachers().size());
            department.setNumCourses(department.getCourses().size());
        }

        model.addAttribute("departments",departmentList);
        return "departments";

    }
    @GetMapping("/departments/new")
    public String createDepartmentForm(Model model) {
        Department department = new Department();
        model.addAttribute("department",department);
        return "newDepartment";
    }

    @PostMapping("/departments")
    public String saveDepartment(@ModelAttribute("department")Department department) {
        if(departmentService.existingDepartment(department.getDepartmentName())) {
            return "redirect:/departments/new?registrationFailed";
        }
        departmentService.saveDepartment(department);
        return "redirect:/departments";
    }

    @GetMapping("/departments/edit/{id}")
    public String editDepartmentForm(@PathVariable Long id, Model model){
        model.addAttribute("department",departmentService.getDepartmentById(id));
        return "editDepartment";
    }

    @PostMapping("/departments/{id}")
    public String updateDepartment(@PathVariable Long id, @ModelAttribute("department") Department department) {

        Department dep = departmentService.getDepartmentById(id);
        dep.setId(id);
        dep.setDepartmentName(department.getDepartmentName());
        dep.setCourses(department.getCourses());
        dep.setTeachers(department.getTeachers());
        departmentService.updateDepartment(dep);
        return "redirect:/departments";
    }

    @GetMapping("/departments/{id}")
    public String deleteDepartment(@PathVariable Long id){
        departmentService.deleteDepartment(id);
        return "redirect:/departments";
    }
}
