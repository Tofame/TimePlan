package com.studencki.TimePlan.services;


import com.studencki.TimePlan.models.Activity;
import com.studencki.TimePlan.models.Student;
import com.studencki.TimePlan.models.Subject;
import com.studencki.TimePlan.repositories.ActivityRepository;
import com.studencki.TimePlan.repositories.StudentRepository;
import com.studencki.TimePlan.repositories.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ActivityRepository activityRepository;

    public SubjectService(SubjectRepository subjectRepository, ActivityRepository activityRepository) {
        this.subjectRepository = subjectRepository;
        this.activityRepository = activityRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<Subject> getSubjectsByLikeName(String name) {
        return subjectRepository.findByNameStartingWithIgnoreCase(name);
    }

    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Optional<Subject> editSubject(Long id, Subject updatedSubject) {
        return subjectRepository.findById(id).map(existingSubject -> {
            existingSubject.setName(updatedSubject.getName());
            existingSubject.setEtcs(updatedSubject.getEtcs());
            existingSubject.setCodeName(updatedSubject.getCodeName());
            return subjectRepository.save(existingSubject);
        });
    }

    @Transactional
    public boolean deleteSubject(Long id) {
        if (subjectRepository.existsById(id)) {
            List<Activity> activities = activityRepository.findAllBySubjectId(id);
            for (Activity activity : activities) {
                activity.setSubject(null);
            }
            activityRepository.saveAll(activities);

            subjectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}