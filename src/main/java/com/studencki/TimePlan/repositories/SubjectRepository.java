package com.studencki.TimePlan.repositories;

import com.studencki.TimePlan.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByName(String name);
    List<Subject> findByNameStartingWithIgnoreCase(String prefix);
}