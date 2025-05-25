package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.services.ActivityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class TimeTableController {
    private final ActivityService activityService;

    public TimeTableController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }
}