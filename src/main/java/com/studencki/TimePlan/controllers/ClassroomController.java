package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.models.Classroom;
import com.studencki.TimePlan.models.Subject;
import com.studencki.TimePlan.services.ClassroomService;
import com.studencki.TimePlan.services.SubjectService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<Classroom> getAll() {
        return classroomService.findAll();
    }

    @PostMapping
    public Classroom addClassroom(@RequestBody Classroom classroom) {
        return classroomService.addClassroom(classroom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classroom> editSubject(@PathVariable Long id, @RequestBody Classroom updatedClassroom) {
        Optional<Classroom> classroom = classroomService.editClassroom(id, updatedClassroom);
        if (classroom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(classroom.get());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        boolean deleted = classroomService.deleteClassroom(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}