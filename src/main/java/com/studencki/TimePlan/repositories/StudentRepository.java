package com.studencki.TimePlan.repositories;

import com.studencki.TimePlan.models.Student;
import com.studencki.TimePlan.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByName(String name);
    List<Student> findByNameStartingWithIgnoreCase(String prefix);
    int countByGroupLectureId(Long groupNumber);
    int countByGroupLessonId(Long groupNumber);
    Optional<Student> findByStudentIndexAndPassword(String studentIndex, String password);
    Optional<Student> findByStudentIndex(String studentIndex);
}