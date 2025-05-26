package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.dtos.ActivityDTO;
import com.studencki.TimePlan.dtos.ActivityGroupDTO;
import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.ActivityGroup;
import com.studencki.TimePlan.models.ActivityType;
import com.studencki.TimePlan.services.ActivityGroupService;
import com.studencki.TimePlan.services.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data/activitiesGroups")
public class ActivityGroupController {

    private final ActivityGroupService activityGroupService;

    public ActivityGroupController(ActivityGroupService activityGroupService) {
        this.activityGroupService = activityGroupService;
    }

    @GetMapping
    public List<ActivityGroup> getAll() {
        return activityGroupService.findAll();
    }

    @PostMapping
    public ActivityGroup addGroup(@RequestBody ActivityGroupDTO activityGroupDTO) {
        return activityGroupService.addGroup(activityGroupDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityGroup> editGroup(@PathVariable Long id, @RequestBody ActivityGroup updatedGroup) {
        Optional<ActivityGroup> group = activityGroupService.editGroup(id, updatedGroup);
        if (group.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(group.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        if (activityGroupService.findById(id).isPresent()) {
            activityGroupService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityGroupDTO> getGroupInfoById(@PathVariable Long id) {
        Optional<ActivityGroupDTO> group = activityGroupService.getGroupInfoById(id);
        return group.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/groupNumber")
    public ResponseEntity<Integer> getGroupNumberById(@PathVariable Long id) {
        Optional<Integer> groupNumber = activityGroupService.getGroupNumberById(id);
        return groupNumber.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}