package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.models.Teacher;
import com.studencki.TimePlan.services.TeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/search") // Ex: /teachers/search?name=Jan
    public List<Teacher> getTeachersByName(@RequestParam String name) {
        return teacherService.getTeachersByLikeName(name);
    }
}
