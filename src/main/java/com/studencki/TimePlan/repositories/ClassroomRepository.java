package com.studencki.TimePlan.repositories;

import com.studencki.TimePlan.models.Classroom;
import com.studencki.TimePlan.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByAdress(String adress);
    List<Classroom> findByAdressStartingWithIgnoreCase(String prefix);
}