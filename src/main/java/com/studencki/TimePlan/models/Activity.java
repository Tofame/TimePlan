package com.studencki.TimePlan.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "group_id")
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = true)
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = true)
    private Classroom classroom;
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = true)
    private Teacher teacher;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "start_time")
    private LocalDateTime startTime;
    private int duration;
}