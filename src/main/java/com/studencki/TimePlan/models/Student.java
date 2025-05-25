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
    @Column(name = "group_lesson")
    private int groupLesson;
    @Column(name = "group_lecture")
    private int groupLecture;
    private String password;
}