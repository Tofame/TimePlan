package com.studencki.TimePlan.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StudentGroupDTO {
    private int groupLesson;
    private int groupLecture;

    public StudentGroupDTO(int groupLecture, int groupLesson) {
        this.groupLecture = groupLecture;
        this.groupLesson = groupLesson;
    }
}