package com.studencki.TimePlan.services;


import com.studencki.TimePlan.dtos.StudentGroupDTO;
import com.studencki.TimePlan.models.Student;
import com.studencki.TimePlan.models.Teacher;
import com.studencki.TimePlan.repositories.StudentRepository;
import com.studencki.TimePlan.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByName(name);
    }

    public List<Student> getStudentsByLikeName(String name) {
        return studentRepository.findByNameStartingWithIgnoreCase(name);
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student editStudent(Long id, Student updateStudent) {
        Student existing = studentRepository.findById(id).orElseThrow();
        existing.setName(updateStudent.getName());
        existing.setStudentIndex(updateStudent.getStudentIndex());
        existing.setGroupLessonId(updateStudent.getGroupLessonId());
        existing.setGroupLectureId(updateStudent.getGroupLectureId());
        existing.setPassword(updateStudent.getPassword());
        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public boolean studentExists(String studentIndex, String password) {
        return studentRepository.findByStudentIndexAndPassword(studentIndex, password).isPresent();
    }

    public Optional<StudentGroupDTO> getStudentGroups(String studentIndex) {
        return studentRepository.findByStudentIndex(studentIndex)
                .map(student -> new StudentGroupDTO(
                        student.getGroupLectureId(),
                        student.getGroupLessonId()
                ));
    }
}