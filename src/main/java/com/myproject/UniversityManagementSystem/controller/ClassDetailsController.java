package com.myproject.UniversityManagementSystem.controller;

import com.myproject.UniversityManagementSystem.model.Classes;
import com.myproject.UniversityManagementSystem.model.Course;
import com.myproject.UniversityManagementSystem.model.Result;
import com.myproject.UniversityManagementSystem.model.Student;
import com.myproject.UniversityManagementSystem.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class ClassDetailsController {

    private ResultService resultService;
    private ClassService classService;
    private CourseService courseService;
    private DepartmentService departmentService;
    private StudentService studentService;
    private Long mainDepartmentId;
    private Long mainCourseId;
    private Long mainClassId;

    public ClassDetailsController(ResultService resultService, ClassService classService,
                                  CourseService courseService,
                                  DepartmentService departmentService, StudentService studentService) {
        this.resultService = resultService;
        this.classService = classService;
        this.courseService = courseService;
        this.departmentService = departmentService;
        this.studentService = studentService;
    }


    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/details-class{classId}")
    public String listClassDetails(@PathVariable Long departmentId, @PathVariable Long classId,
                                   @PathVariable Long courseId, Model model) {
        Classes mainClass = classService.getClassById(classId);
        mainDepartmentId = departmentId;
        mainCourseId = courseId;
        mainClassId = classId;
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("className",mainClass.getClassName());
        model.addAttribute("class",mainClass);
        model.addAttribute("results", resultService.findByClass(mainClass));
        return "classDetails";
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/details-class{classId}/newResult")
    public String createResultForm(Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("class",classService.getClassById(mainClassId));
        Result result = new Result();
        model.addAttribute("results",result);
        model.addAttribute("students", studentService.findByCourse(courseService.findCourseById(mainCourseId)));
        return "newResult";
    }

    @PostMapping("/departments/details-d{departmentId}/details-c{courseId}/details-class{classId}/newResult/")
    public String saveResult(@ModelAttribute("results") Result result, Model model) {
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("class",classService.getClassById(mainClassId));
        result.setClasses(classService.getClassById(mainClassId));
        if(resultService.existingResult(result.getStudent().getStudentId())) {
            return "redirect:/departments/details-d{departmentId}/details-c{courseId}/details-class{classId}/newResult?registrationFailed";
        }
        resultService.saveResult(result);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}/details-class{classId}/newResult";
    }


    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/details-class{classId}/r-{resultId}")
    public String deleteResult(@PathVariable Long resultId){
        resultService.deleteResult(resultId);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}/details-class{classId}";
    }
}
