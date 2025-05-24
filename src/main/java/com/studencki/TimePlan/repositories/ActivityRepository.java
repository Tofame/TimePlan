package com.studencki.TimePlan.repositories;

import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}