package com.studencki.TimePlan.services;


import com.studencki.TimePlan.dtos.ActivityDTO;
import com.studencki.TimePlan.dtos.ActivityGroupDTO;
import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.ActivityGroup;
import com.studencki.TimePlan.models.ActivityType;
import com.studencki.TimePlan.models.Student;
import com.studencki.TimePlan.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityGroupService {

    private final ActivityGroupRepository activityGroupRepository;
    private final StudentRepository studentRepository;

    public ActivityGroupService(ActivityGroupRepository activityGroupRepository, StudentRepository studentRepository) {
        this.activityGroupRepository = activityGroupRepository;
        this.studentRepository = studentRepository;
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

    @Transactional
    public boolean deleteById(Long id) {
        Optional<ActivityGroup> optionalGroup = activityGroupRepository.findById(id);
        if (optionalGroup.isEmpty()) {
            return false;
        }

        ActivityGroup group = optionalGroup.get();

        List<Student> lessonStudents = studentRepository.findAllByGroupLessonId(group.getId());
        for (Student s : lessonStudents) {
            s.setGroupLessonId(null);
        }

        List<Student> lectureStudents = studentRepository.findAllByGroupLectureId(group.getId());
        for (Student s : lectureStudents) {
            s.setGroupLectureId(null);
        }

        studentRepository.saveAll(lessonStudents);
        studentRepository.saveAll(lectureStudents);

        activityGroupRepository.delete(group);
        return true;
    }

    public Optional<ActivityGroup> editGroup(Long id, ActivityGroup updatedGroup) {
        return activityGroupRepository.findById(id)
        .map(existingGroup -> {
            existingGroup.setType(updatedGroup.getType());
            existingGroup.setGroupNumber(updatedGroup.getGroupNumber());
            return activityGroupRepository.save(existingGroup);
        });
    }

    public boolean deleteByTypeAndGroupNumber(ActivityGroupDTO dto) {
        Optional<ActivityGroup> groupOpt = activityGroupRepository.findByTypeAndGroupNumber(ActivityType.fromValue(dto.getType()), dto.getGroupNumber());
        if (groupOpt.isPresent()) {
            activityGroupRepository.delete(groupOpt.get());
            return true;
        }
        return false;
    }

    public Optional<Integer> getGroupNumberById(Long id) {
        return findById(id).map(ActivityGroup::getGroupNumber);
    }

    public ActivityType getGroupTypeById(Long groupId) {
        return activityGroupRepository.findById(groupId)
                .map(ActivityGroup::getType)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
    }
}