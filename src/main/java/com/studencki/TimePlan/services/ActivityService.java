package com.studencki.TimePlan.services;


import com.studencki.TimePlan.dtos.ActivityDTO;
import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.ActivityType;
import com.studencki.TimePlan.models.Classroom;
import com.studencki.TimePlan.repositories.ActivityRepository;
import com.studencki.TimePlan.repositories.ClassroomRepository;
import com.studencki.TimePlan.repositories.SubjectRepository;
import com.studencki.TimePlan.repositories.TeacherRepository;
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

    public ActivityService(
        ActivityRepository activityRepository,
       SubjectRepository subjectRepository,
       TeacherRepository teacherRepository,
       ClassroomRepository classroomRepository
    ) {
        this.activityRepository = activityRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
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

    public Activity convertDtoToEntity(ActivityDTO dto) {
        Activity activity = new Activity();
        activity.setSubject(subjectRepository.findById(dto.getSubject_id()).orElse(null));
        activity.setTeacher(teacherRepository.findById(dto.getTeacher_id()).orElse(null));
        activity.setClassroom(classroomRepository.findById(dto.getClassroom_id()).orElse(null));
        activity.setStudent_count(dto.getStudent_count());
        activity.setStart_time(LocalDateTime.parse(dto.getStart_time()));
        activity.setDuration(dto.getDuration());
        activity.setType(ActivityType.values()[dto.getType()]);
        activity.setGroup_number(dto.getGroup_number() != null ? dto.getGroup_number() : 0);
        return activity;
    }
}