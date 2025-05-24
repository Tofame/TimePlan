package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.models.Student;
import com.studencki.TimePlan.services.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/search")  // Ex: /students/search?name=Zof
    public List<Student> getStudentsByName(@RequestParam String name) {
        return studentService.getStudentsByLikeName(name);
    }
}