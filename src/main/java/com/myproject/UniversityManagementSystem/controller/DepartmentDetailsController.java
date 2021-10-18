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
        Set<Course> coursesList = courseService.findByDepartment(mainDepartment);

        for(Course course : coursesList) {
            int numClasses = courseService.countNumClasses(course.getCourseId());
            int numStudents = courseService.countNumStudents(course.getCourseId());
            course.setNumClasses(numClasses);
            course.setNumStudents(numStudents);
        }

        mainDepartmentId = departmentId;
        model.addAttribute("department",mainDepartment);
        model.addAttribute("departmentName",mainDepartment.getDepartmentName());
        model.addAttribute("courses",coursesList);

        return "departmentDetails";
    }

    @GetMapping("/departments/details-d{departmentId}/teachers")
    public String listDepartmentTeachers(Model model) {
        Department mainDepartment = departmentService.getDepartmentById(mainDepartmentId);
        Set<Teacher> teachersList = teacherService.findTeacherByDepartment(mainDepartment);

        for(Teacher teacher : teachersList) {
            int numClasses = teacherService.numberClasses(teacher);
            teacher.setNumClasses(numClasses);
        }
        model.addAttribute("department",mainDepartment);
        model.addAttribute("departmentName",mainDepartment.getDepartmentName());
        model.addAttribute("teachers", teachersList);

        return "teachers";
    }

    @GetMapping("/departments/details-d{departmentId}/newCourse")
    public String createCourseForm(Model model) {
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Course course = new Course();
        model.addAttribute("courses",course);
        return "newCourse";
    }

    @PostMapping(value = {"/departments/details-d{departmentId}/save{id}"})
    public String saveCourse(@ModelAttribute("courses")Course course, Model model) {

        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        course.setDepartment(departmentService.getDepartmentById(mainDepartmentId));
        if(courseService.existingCourse(course)) {
            return "redirect:/departments/details-d{departmentId}/newCourse?registrationFailed";
        }
        courseService.saveCourse(course);
        return "redirect:/departments";
    }


    @GetMapping("/departments/details-d{departmentId}/edit-c{courseId}")
    public String editCourseForm(@PathVariable Long courseId, Model model){
        Course course = courseService.findCourseById(courseId);
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("courses",course);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "editCourse";
    }

    @PostMapping(value={"/departments/details-d{departmentId}/edited-c{courseId}"})
    public String updateCourse(@PathVariable Long courseId,@ModelAttribute("courses") Course course,
                               Model model) {

        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Course course1 = courseService.findCourseById(courseId);
        course1.setCourseId(courseId);
        course1.setDepartment(course.getDepartment());
        course1.setName(course.getName());
        course1.setDuration(course.getDuration());
        course1.setNumStudents(course.getNumStudents());
        course1.setStudents(course.getStudents());
        course1.setClasses(course.getClasses());

        courseService.saveCourse(course1);

        return "redirect:/departments/details-d{departmentId}";
    }


    @GetMapping("/departments/details-d{departmentId}/cr-{courseId}")
    public String deleteCourse(@PathVariable Long courseId,Model model){
        courseService.deleteCourse(courseId);
        return "redirect:/departments/details-d{departmentId}";
    }

    @GetMapping("/departments/details-d{departmentId}/newTeacher")
    public String createTeacherForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("teachers", teacher);
        return "newTeacher";
    }

    @PostMapping("/departments/details/{departmentId}")
    public String saveTeacher(@PathVariable Long departmentId,@ModelAttribute("teachers")Teacher teacher, Model model) {
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        teacher.setDepartment(departmentService.getDepartmentById(mainDepartmentId));
        if(teacherService.existingTeacher(teacher)) {
            return "redirect:/departments/details-d{departmentId}/newTeacher?registrationFailed";
        }
        teacherService.saveTeacher(teacher);
        return "redirect:/departments/details-d{departmentId}";
    }

    @GetMapping("/departments/details-d{departmentId}/edit-t{teacherId}")
    public String editTeacherForm(@PathVariable Long teacherId, Model model){
        Teacher teacher = teacherService.findTeacherById(teacherId);
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("teachers",teacher);
        return "editTeacher";
    }

    @PostMapping(value={"/departments/details-d{departmentId}/edited-t{teacherId}"})
    public String updateTeacher(@PathVariable Long teacherId,@ModelAttribute("teachers") Teacher teacher,
                                   Model model) {
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Teacher teacher1 = teacherService.findTeacherById(teacherId);
        teacher1.setId(teacherId);
        teacher1.setTeacherName(teacher.getTeacherName());
        teacher1.setAddress(teacher.getAddress());
        teacher1.setEmail(teacher.getEmail());
        teacher1.setDateBirth(teacher.getDateBirth());
        teacher1.setDepartment(teacher.getDepartment());
        teacher1.setPhoneNumber(teacher.getPhoneNumber());
        teacher1.setClasses(teacher.getClasses());

        teacherService.saveTeacher(teacher1);
        return "redirect:/departments/details-d{departmentId}";
    }


    @GetMapping("/departments/details-d{departmentId}/td-{teacherId}/")
    public String deleteTeacher(@PathVariable Long teacherId){
        teacherService.deleteTeacher(teacherId);
        return "redirect:/departments/details-d{departmentId}";
    }




}
