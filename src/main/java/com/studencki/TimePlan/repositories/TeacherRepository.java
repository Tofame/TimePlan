package com.studencki.TimePlan.repositories;

import com.studencki.TimePlan.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByName(String name);
    List<Teacher> findByNameStartingWithIgnoreCase(String name);
}