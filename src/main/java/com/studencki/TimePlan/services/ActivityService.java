package com.studencki.TimePlan.services;


import com.studencki.TimePlan.dtos.ActivityDTO;
import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.ActivityType;
import com.studencki.TimePlan.models.Classroom;
import com.studencki.TimePlan.repositories.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final ActivityGroupRepository groupRepository;
    private final ActivityGroupService activityGroupService;

    public ActivityService(
        ActivityRepository activityRepository,
       SubjectRepository subjectRepository,
       TeacherRepository teacherRepository,
       ClassroomRepository classroomRepository,
       StudentRepository studentRepository,
        ActivityGroupRepository groupRepository,
        ActivityGroupService activityGroupService
    ) {
        this.activityRepository = activityRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.activityGroupService = activityGroupService;
    }

    public List<Activity> getAllActivities() {
        return getAllActivities("id", true);
    }

    public List<Activity> getAllActivities(String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return activityRepository.findAll(sort);
    }

    public Activity addActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Optional<Activity> editActivity(Long id, Activity updatedActivity) {
        return activityRepository.findById(id).map(activity -> {
            activity.setGroupId(updatedActivity.getGroupId());
            activity.setSubject(updatedActivity.getSubject());
            activity.setClassroom(updatedActivity.getClassroom());
            activity.setTeacher(updatedActivity.getTeacher());
            activity.setStartTime(updatedActivity.getStartTime());
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

    public Activity convertDtoToEntity(ActivityDTO dto) {
        Activity activity = new Activity();
        activity.setSubject(subjectRepository.findById(dto.getSubjectId()).orElse(null));
        activity.setTeacher(teacherRepository.findById(dto.getTeacherId()).orElse(null));
        activity.setClassroom(classroomRepository.findById(dto.getClassroomId()).orElse(null));
        activity.setStartTime(LocalDateTime.parse(dto.getStartTime()));
        activity.setDuration(dto.getDuration());
        activity.setGroupId(dto.getGroupId() != null ? dto.getGroupId() : 0);
        return activity;
    }

    public int getStudentCount(Activity activity) {
        ActivityType groupType = activityGroupService.getGroupTypeById(activity.getGroupId());

        if (groupType == ActivityType.LECTURE) {
            return studentRepository.countByGroupLectureId(activity.getGroupId());
        } else {
            return studentRepository.countByGroupLessonId(activity.getGroupId());
        }
    }

    public List<Activity> getLectureActivities(int groupId) {
        return activityRepository.findByGroupId(groupId);
    }

    public List<Activity> getLessonActivities(int groupId) {
        return activityRepository.findByGroupId(groupId);
    }
}