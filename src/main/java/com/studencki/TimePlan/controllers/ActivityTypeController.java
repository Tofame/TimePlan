package com.studencki.TimePlan.controllers;

import com.studencki.TimePlan.models.ActivityType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/enums")
public class ActivityTypeController {

    @GetMapping("/activity-types")
    public Map<Integer, String> getActivityTypes() {
        Map<Integer, String> map = new LinkedHashMap<>();
        for (ActivityType type : ActivityType.values()) {
            map.put(type.getValue(), type.name());
        }
        return map;
    }
}