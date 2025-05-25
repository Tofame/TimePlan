package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.dtos.LoginInfoDTO;
import com.studencki.TimePlan.models.JwtUtils;
import com.studencki.TimePlan.models.Student;
import com.studencki.TimePlan.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/data/students")
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

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("/{id}")
    public Student editStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentService.editStudent(id, updatedStudent);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginInfoDTO credentials) {
        String login = credentials.getStudentIndex();
        String password = credentials.getPassword();

        boolean exists = studentService.studentExists(login, password);

        if (!exists) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = JwtUtils.generateToken(login);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        System.out.println("res " + response);
        return ResponseEntity.ok(response);
    }
}