package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.models.Teacher;
import com.studencki.TimePlan.services.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> editTeacher(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        Optional<Teacher> teacher = teacherService.editTeacher(id, updatedTeacher);
        if (teacher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teacher.get());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        boolean deleted = teacherService.deleteTeacher(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
