package com.studencki.TimePlan.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StudentGroupDTO {
    private Long groupLessonId;
    private Long groupLectureId;

    public StudentGroupDTO(Long groupLectureId, Long groupLessonId) {
        this.groupLectureId = groupLectureId;
        this.groupLessonId = groupLessonId;
    }
}