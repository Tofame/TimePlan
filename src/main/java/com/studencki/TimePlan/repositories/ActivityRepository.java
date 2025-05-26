package com.studencki.TimePlan.repositories;

import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.ActivityType;
import com.studencki.TimePlan.models.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByTeacherId(Long teacherId);
    List<Activity> findAllBySubjectId(Long subjectId);
    List<Activity> findAllByClassroomId(Long classroomId);
    List<Activity> findByGroupId(int groupId);
}