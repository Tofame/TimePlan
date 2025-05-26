package com.studencki.TimePlan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "student_index")
    private String studentIndex;
    private String password;

    @Column(name = "group_lesson_id")
    private Long groupLessonId;

    @Column(name = "group_lecture_id")
    private Long groupLectureId;
}