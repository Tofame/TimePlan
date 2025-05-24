package com.studencki.TimePlan.services;


import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.Student;
import com.studencki.TimePlan.repositories.ActivityRepository;
import com.studencki.TimePlan.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}