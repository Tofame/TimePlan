package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.models.Subject;
import com.studencki.TimePlan.models.Teacher;
import com.studencki.TimePlan.services.SubjectService;
import com.studencki.TimePlan.services.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/search") // Ex: /subjects/search?name=Technologie
    public List<Subject> getSubjectsByName(@RequestParam String name) {
        return subjectService.getSubjectsByLikeName(name);
    }

    @PostMapping
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectService.addSubject(subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> editSubject(@PathVariable Long id, @RequestBody Subject updatedSubject) {
        Optional<Subject> subject = subjectService.editSubject(id, updatedSubject);
        if (subject.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subject.get());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        boolean deleted = subjectService.deleteSubject(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}