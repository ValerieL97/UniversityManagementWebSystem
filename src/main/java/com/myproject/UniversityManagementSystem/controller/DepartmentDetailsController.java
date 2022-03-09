package com.myproject.UniversityManagementSystem.controller;

import com.myproject.UniversityManagementSystem.model.Course;
import com.myproject.UniversityManagementSystem.model.Department;
import com.myproject.UniversityManagementSystem.model.Teacher;
import com.myproject.UniversityManagementSystem.service.CourseService;
import com.myproject.UniversityManagementSystem.service.DepartmentService;
import com.myproject.UniversityManagementSystem.service.TeacherService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class DepartmentDetailsController {

    private CourseService courseService;
    private TeacherService teacherService;
    private DepartmentService departmentService;
    private Long mainDepartmentId;

    public DepartmentDetailsController(CourseService courseService,
                                       TeacherService teacherService,
                                       DepartmentService departmentService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.departmentService = departmentService;
    }

    @GetMapping("/departments/details-d{departmentId}")
    public String listDepartmentsCourses(@PathVariable Long departmentId, Model model) {
        Department mainDepartment = departmentService.getDepartmentById(departmentId);

        for(Course course : mainDepartment.getCourses()) {
            course.setNumClasses(course.getClasses().size());
            course.setNumStudents(course.getStudents().size());
        }

        mainDepartmentId = departmentId;
        model.addAttribute("department",mainDepartment);

        return "departmentDetails";
    }

    @GetMapping("/departments/details-d{departmentId}/teachers")
    public String listDepartmentTeachers(Model model) {
        Department mainDepartment = departmentService.getDepartmentById(mainDepartmentId);

        for(Teacher teacher : mainDepartment.getTeachers()) {
            teacher.setNumClasses(teacher.getClasses().size());
        }
        model.addAttribute("department",mainDepartment);

        return "teachers";
    }


    @GetMapping("/departments/details-d{departmentId}/newCourse")
    public String createCourseForm(Model model) {
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Course course = new Course();
        model.addAttribute("course",course);
        return "newCourse";
    }

    @PostMapping(value = {"/departments/details-d{departmentId}/save{id}"})
    public String saveCourse(@ModelAttribute("course")Course course, Model model) {

        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        course.setDepartment(departmentService.getDepartmentById(mainDepartmentId));
        Course savedCourse = courseService.saveCourse(course);
        if(savedCourse != null) {
            return "redirect:/departments";

        }
        return "redirect:/departments/details-d{departmentId}/newCourse?registrationFailed";
    }


    @GetMapping("/departments/details-d{departmentId}/edit-c{courseId}")
    public String editCourseForm(@PathVariable Long courseId, Model model){
        Course course = courseService.findCourseById(courseId);
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("course",course);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "editCourse";
    }

    @PostMapping(value={"/departments/details-d{departmentId}/edited-c{courseId}"})
    public String updateCourse(@PathVariable Long courseId,@ModelAttribute("course") Course course,
                               Model model) {

        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        courseService.updateCourseInfo(courseId,course);
        return "redirect:/departments/details-d{departmentId}";
    }


    @GetMapping("/departments/details-d{departmentId}/cr-{courseId}")
    public String deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
        return "redirect:/departments/details-d{departmentId}";
    }

    @GetMapping("/departments/details-d{departmentId}/newTeacher")
    public String createTeacherForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("teacher", teacher);
        return "newTeacher";
    }

    @PostMapping("/departments/details/{departmentId}")
    public String saveTeacher(@PathVariable Long departmentId,@ModelAttribute("teachers")Teacher teacher, Model model) {
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        teacher.setDepartment(departmentService.getDepartmentById(mainDepartmentId));
        Teacher savedTeacher = teacherService.saveTeacher(teacher);
        if(savedTeacher == null) {
            return "redirect:/departments/details-d{departmentId}/newTeacher?registrationFailed";
        }
        return "redirect:/departments/details-d{departmentId}";
    }

    @GetMapping("/departments/details-d{departmentId}/edit-t{teacherId}")
    public String editTeacherForm(@PathVariable Long teacherId, Model model){
        Teacher teacher = teacherService.findTeacherById(teacherId);
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("teacher",teacher);
        return "editTeacher";
    }

    @PostMapping(value={"/departments/details-d{departmentId}/edited-t{teacherId}"})
    public String updateTeacher(@PathVariable Long teacherId,@ModelAttribute("teacher") Teacher teacher,
                                   Model model) {
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        teacherService.updateTeacherInfo(teacher,teacherId);
        return "redirect:/departments/details-d{departmentId}";
    }


    @GetMapping("/departments/details-d{departmentId}/td-{teacherId}/")
    public String deleteTeacher(@PathVariable Long teacherId){
        teacherService.deleteTeacher(teacherId);
        return "redirect:/departments/details-d{departmentId}";
    }




}
