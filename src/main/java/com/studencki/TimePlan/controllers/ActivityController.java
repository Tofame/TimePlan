package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.dtos.ActivityDTO;
import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.ActivityType;
import com.studencki.TimePlan.models.Classroom;
import com.studencki.TimePlan.services.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data/activities")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // Ex: /data/activities?sortBy=duration&asc=true
    @GetMapping
    public List<Activity> getAllActivities(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc
    ) {
        return activityService.getAllActivities(sortBy, asc);
    }

    @GetMapping("/lectures/{groupId}")
    public List<Activity> getAllLectures(@PathVariable int groupId) {
        return activityService.getLectureActivities(groupId);
    }

    @GetMapping("/lessons/{groupId}")
    public List<Activity> getAllLessons(@PathVariable int groupId) {
        return activityService.getLessonActivities(groupId);
    }

    @PostMapping
    public ResponseEntity<Activity> addActivity(@RequestBody ActivityDTO dto) {
        Activity activity = activityService.convertDtoToEntity(dto);
        Activity created = activityService.addActivity(activity);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> editActivity(@PathVariable Long id, @RequestBody ActivityDTO updatedActivityDTO) {
        Activity updatedActivity = activityService.convertDtoToEntity(updatedActivityDTO);
        Optional<Activity> activity = activityService.editActivity(id, updatedActivity);
        if (activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activity.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        boolean deleted = activityService.deleteActivity(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable Long id) {
        Optional<Activity> activity = activityService.getActivityById(id);
        if (activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activity.get());
    }

    @GetMapping("/{id}/studentCount")
    public ResponseEntity<Integer> getStudentCount(@PathVariable Long id) {
        Optional<Activity> activityOptional = activityService.getActivityById(id);
        if (activityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int studentCount = activityService.getStudentCount(activityOptional.get());
        return ResponseEntity.ok(studentCount);
    }
}