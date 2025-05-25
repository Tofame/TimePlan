package com.studencki.TimePlan.services;


import com.studencki.TimePlan.models.Teacher;
import com.studencki.TimePlan.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Optional<Teacher> editTeacher(Long id, Teacher updatedTeacher) {
        return teacherRepository.findById(id).map(existing -> {
            existing.setName(updatedTeacher.getName());
            existing.setTitle(updatedTeacher.getTitle());
            existing.setAge(updatedTeacher.getAge());
            return teacherRepository.save(existing);
        });
    }

    public boolean deleteTeacher(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return true;
        }
        return false;
    }
}