package com.studencki.TimePlan.services;


import com.studencki.TimePlan.dtos.ActivityDTO;
import com.studencki.TimePlan.dtos.ActivityGroupDTO;
import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.ActivityGroup;
import com.studencki.TimePlan.models.ActivityType;
import com.studencki.TimePlan.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityGroupService {

    private final ActivityGroupRepository activityGroupRepository;

    public ActivityGroupService(ActivityGroupRepository activityGroupRepository) {
        this.activityGroupRepository = activityGroupRepository;
    }

    public List<ActivityGroup> findAll() {
        return activityGroupRepository.findAll();
    }

    public Optional<ActivityGroup> findById(Long id) {
        return activityGroupRepository.findById(id);
    }

    public ActivityGroup addGroup(ActivityGroupDTO activityGroupDTO) {
        ActivityGroup activityGroup = new ActivityGroup();
        activityGroup.setType(ActivityType.fromValue(activityGroupDTO.getType()));
        activityGroup.setGroupNumber(activityGroupDTO.getGroupNumber());
        return activityGroupRepository.save(activityGroup);
    }

    public void deleteById(Long id) {
        activityGroupRepository.deleteById(id);
    }

    public Optional<ActivityGroup> editGroup(Long id, ActivityGroup updatedGroup) {
        return activityGroupRepository.findById(id)
        .map(existingGroup -> {
            existingGroup.setType(updatedGroup.getType());
            existingGroup.setGroupNumber(updatedGroup.getGroupNumber());
            return activityGroupRepository.save(existingGroup);
        });
    }
}