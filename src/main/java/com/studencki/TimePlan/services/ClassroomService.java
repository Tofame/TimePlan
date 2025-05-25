package com.studencki.TimePlan.services;


import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.Classroom;
import com.studencki.TimePlan.models.Subject;
import com.studencki.TimePlan.repositories.ActivityRepository;
import com.studencki.TimePlan.repositories.ClassroomRepository;
import com.studencki.TimePlan.repositories.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ActivityRepository activityRepository;

    public ClassroomService(ClassroomRepository classroomRepository, ActivityRepository activityRepository) {
        this.classroomRepository = classroomRepository;
        this.activityRepository = activityRepository;
    }

    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    public Optional<Classroom> findById(Long id) {
        return classroomRepository.findById(id);
    }

    public Classroom addClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public Optional<Classroom> editClassroom(Long id, Classroom updatedClassroom) {
        return classroomRepository.findById(id).map(classroom -> {
            classroom.setAdress(updatedClassroom.getAdress());
            return classroomRepository.save(classroom);
        });
    }

    @Transactional
    public boolean deleteClassroom(Long id) {
        if (classroomRepository.existsById(id)) {
            List<Activity> activities = activityRepository.findAllByClassroomId(id);
            for (Activity activity : activities) {
                activity.setClassroom(null);
            }
            activityRepository.saveAll(activities);

            classroomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}