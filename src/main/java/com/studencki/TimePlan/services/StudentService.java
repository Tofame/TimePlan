package com.studencki.TimePlan.services;


import com.studencki.TimePlan.models.Student;
import com.studencki.TimePlan.models.Teacher;
import com.studencki.TimePlan.repositories.StudentRepository;
import com.studencki.TimePlan.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByName(name);
    }

    public List<Student> getStudentsByLikeName(String name) {
        return studentRepository.findByNameStartingWithIgnoreCase(name);
    }
}