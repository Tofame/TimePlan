package com.studencki.TimePlan.repositories;

import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.ActivityGroup;
import com.studencki.TimePlan.models.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityGroupRepository extends JpaRepository<ActivityGroup, Long> {
}