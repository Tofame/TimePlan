package com.studencki.TimePlan.services;


import com.studencki.TimePlan.models.Teacher;
import com.studencki.TimePlan.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public List<Teacher> getTeachersByName(String name) {
        return teacherRepository.findByName(name);
    }

    public List<Teacher> getTeachersByLikeName(String name) {
        return teacherRepository.findByNameStartingWithIgnoreCase(name);
    }
}