package com.studencki.TimePlan.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StudentGroupDTO {
    private Long groupLesson;
    private Long groupLecture;

    public StudentGroupDTO(Long groupLecture, Long groupLesson) {
        this.groupLecture = groupLecture;
        this.groupLesson = groupLesson;
    }
}