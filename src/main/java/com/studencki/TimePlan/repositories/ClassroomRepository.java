package com.studencki.TimePlan.repositories;

import com.studencki.TimePlan.models.Classroom;
import com.studencki.TimePlan.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByAddress(String address);
    List<Classroom> findByAddressStartingWithIgnoreCase(String prefix);
}