package com.studencki.TimePlan.services;


import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.Classroom;
import com.studencki.TimePlan.repositories.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity addActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Optional<Activity> editActivity(Long id, Activity updatedActivity) {
        return activityRepository.findById(id).map(activity -> {
            activity.setType(updatedActivity.getType());
            activity.setGroup_number(updatedActivity.getGroup_number());
            activity.setSubject(updatedActivity.getSubject());
            activity.setClassroom(updatedActivity.getClassroom());
            activity.setTeacher(updatedActivity.getTeacher());
            activity.setStudent_count(updatedActivity.getStudent_count());
            activity.setStart_time(updatedActivity.getStart_time());
            activity.setDuration(updatedActivity.getDuration());

            return activityRepository.save(activity);
        });
    }

    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    public boolean deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}