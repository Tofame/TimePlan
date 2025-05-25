package com.studencki.TimePlan.services;


import com.studencki.TimePlan.models.Teacher;
import com.studencki.TimePlan.repositories.ActivityRepository;
import com.studencki.TimePlan.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ActivityRepository activityRepository;

    public TeacherService(TeacherRepository teacherRepository, ActivityRepository activityRepository) {
        this.teacherRepository = teacherRepository;
        this.activityRepository = activityRepository;
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
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();

            // Remove teacher from their activities (foreign key constraint)
            activityRepository.findAllByTeacherId(id).forEach(activity -> {
                activity.setTeacher(null);
                activityRepository.save(activity);
            });

            teacherRepository.delete(teacher);
            return true;
        }
        return false;
    }
}