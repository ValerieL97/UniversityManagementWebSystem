package com.myproject.UniversityManagementSystem.controller;


import com.myproject.UniversityManagementSystem.model.*;
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
public class CourseDetailsController {

    private StudentService studentService;
    private ClassService classService;
    private CourseService courseService;
    private TeacherService teacherService;
    private DepartmentService departmentService;
    private Long mainCourseId;
    private Long mainDepartmentId;

    public CourseDetailsController(StudentService studentService, ClassService classService,
                                   CourseService courseService, TeacherService teacherService,
                                   DepartmentService departmentService) {
        this.studentService = studentService;
        this.classService = classService;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.departmentService = departmentService;
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}")
    public String listCoursesClasses(@PathVariable Long departmentId,@PathVariable Long courseId,
                                     Model model) {
        mainDepartmentId = departmentId;
        mainCourseId = courseId;
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        return "courseDetails";
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/students")
    public String listCourseStudents(@PathVariable Long departmentId,@PathVariable Long courseId,
                                     Model model) {
        mainDepartmentId = departmentId;
        mainCourseId = courseId;
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        return "students";
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/newClass")
    public String createClassForm(Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Classes class1 = new Classes();
        model.addAttribute("class",class1);
        return "newClass";
    }

    @PostMapping("/departments/details-d{departmentId}/")
    public String saveClass(@ModelAttribute("class") Classes class1, Model model) {
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        class1.setCourse(courseService.findCourseById(mainCourseId));
        Classes classes = classService.saveClass(class1);
        if(classes == null) {
            return "redirect:/departments/details-d{departmentId}/details-c{courseId}/newClass?registrationFailed";
        }
        return "redirect:/departments/details-d{departmentId}";
    }


    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/edit-class{classId}")
    public String editClassForm(@PathVariable Long classId, Model model){
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Classes class1 = classService.getClassById(classId);
        model.addAttribute("class",class1);
        return "editClass";
    }

    @PostMapping(value={"/departments/details-d{departmentId}/details-c{courseId}/class-{classId}"})
    public String updateClass(@PathVariable Long classId,@ModelAttribute("class") Classes classes,
                                Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        classService.updateClassInfo(classes,classId);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }


    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/cl-{classId}")
    public String deleteClass(@PathVariable Long classId){
        classService.deleteClass(classId);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/newStudent")
    public String createStudentForm(Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Student student = new Student();
        model.addAttribute("student",student);
        return "newStudent";
    }

    @PostMapping("/departments/details-d{departmentId}/details-c{courseId}")
    public String saveStudent(@ModelAttribute("student")Student student, Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        student.setCourse(courseService.findCourseById(mainCourseId));
        Student savedStudent = studentService.saveStudent(student);
        if(savedStudent == null) {
            return "redirect:/departments/details-d{departmentId}/details-c{courseId}/newStudent?registrationFailed";
        }
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/edit-student{studentId}")
    public String editStudentForm(@PathVariable Long studentId, Model model){
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student",student);
        return "editStudent";
    }

    @PostMapping(value={"/departments/details-d{departmentId}/details-c{courseId}/student-{studentId}"})
    public String updateStudent(@PathVariable Long studentId,@ModelAttribute("student") Student student,
                              Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Student student1 = studentService.updateStudentInfo(studentId,student);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }


    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/st-{studentId}")
    public String deleteStudent(@PathVariable Long studentId,Model model){
        studentService.deleteStudent(studentId);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }
}
