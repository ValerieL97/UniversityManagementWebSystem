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
    public String listCoursesClasses(@PathVariable Long departmentId,@PathVariable Long courseId, Model model) {
        Course mainCourse = courseService.findCourseById(courseId);
        mainDepartmentId = departmentId;
        mainCourseId = courseId;
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("courseName",mainCourse.getName());
        model.addAttribute("classes",classService.findByCourse(mainCourse));
        model.addAttribute("students",studentService.findByCourse(mainCourse));
        return "courseDetails";
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/students")
    public String listCourseStudents(@PathVariable Long departmentId,@PathVariable Long courseId, Model model) {
        Course mainCourse = courseService.findCourseById(courseId);
        mainDepartmentId = departmentId;
        mainCourseId = courseId;
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("courseName",mainCourse.getName());
        model.addAttribute("students",studentService.findByCourse(mainCourse));
        return "students";
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/newClass")
    public String createClassForm(Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Classes classes = new Classes();
        model.addAttribute("classes",classes);
        model.addAttribute("teachers",teacherService.findByDepartment(departmentService.getDepartmentById(mainDepartmentId)));
        return "newClass";
    }

    @PostMapping("/departments/details-d{departmentId}/")
    public String saveClass(@ModelAttribute("classes") Classes class1, Model model) {
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        class1.setCourse(courseService.findCourseById(mainCourseId));
        if(classService.existingClass(class1)) {
            return "redirect:/departments/details-d{departmentId}/details-c{courseId}/newClass?registrationFailed";
        }
        classService.saveClass(class1);
        return "redirect:/departments/details-d{departmentId}";
    }


    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/edit-class{classId}")
    public String editClassForm(@PathVariable Long classId, Model model){
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("courses",courseService.findByDepartment(departmentService.getDepartmentById(mainDepartmentId)));
        Classes classes = classService.getClassById(classId);
        model.addAttribute("classes",classes);
        model.addAttribute("teachers",teacherService.findByDepartment(departmentService.getDepartmentById(mainDepartmentId)));
        return "editClass";
    }

    @PostMapping(value={"/departments/details-d{departmentId}/details-c{courseId}/class-{classId}"})
    public String updateClass(@PathVariable Long classId,@ModelAttribute("classes") Classes classes,
                                Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Classes class1 = classService.getClassById(classId);
        class1.setTeacher(classes.getTeacher());
        class1.setClassId(classId);
        class1.setCourse(classes.getCourse());
        class1.setClassName(classes.getClassName());
        class1.setDuration(classes.getDuration());
        class1.setSemester(classes.getSemester());
        classService.saveClass(class1);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }


    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/cl-{classId}")
    public String deleteClass(@PathVariable Long classId){
        classService.deleteClassById(classId);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/newStudent")
    public String createStudentForm(Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        Student student = new Student();
        model.addAttribute("students",student);
        return "newStudent";
    }

    @PostMapping("/departments/details-d{departmentId}/details-c{courseId}")
    public String saveStudent(@ModelAttribute("students")Student student, Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        student.setCourse(courseService.findCourseById(mainCourseId));
        student.setCode(studentService.generateCodeForLogin());
        if(studentService.existingStudent(student)) {
            return "redirect:/departments/details-d{departmentId}/details-c{courseId}/newStudent?registrationFailed";
        }
        studentService.saveStudent(student);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }

    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/edit-student{studentId}")
    public String editStudentForm(@PathVariable Long studentId, Model model){
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));
        model.addAttribute("courses",courseService.findByDepartment(departmentService.getDepartmentById(mainDepartmentId)));
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("students",student);
        return "editStudent";
    }

    @PostMapping(value={"/departments/details-d{departmentId}/details-c{courseId}/student-{studentId}"})
    public String updateStudent(@PathVariable Long studentId,@ModelAttribute("students") Student student,
                              Model model) {
        model.addAttribute("course",courseService.findCourseById(mainCourseId));
        model.addAttribute("department",departmentService.getDepartmentById(mainDepartmentId));

        Student student1 = studentService.getStudentById(studentId);
        student1.setStudentId(studentId);
        student1.setStudentName(student.getStudentName());
        student1.setDateBirth(student.getDateBirth());
        student1.setCourse(student.getCourse());
        student1.setCode(studentService.getStudentById(studentId).getCode());
        student1.setEmail(student.getEmail());
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setYear(student.getYear());
        studentService.deleteStudentById(studentId);
        studentService.saveStudent(student1);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }


    @GetMapping("/departments/details-d{departmentId}/details-c{courseId}/st-{studentId}")
    public String deleteStudent(@PathVariable Long studentId,Model model){
        studentService.deleteStudentById(studentId);
        return "redirect:/departments/details-d{departmentId}/details-c{courseId}";
    }
}
