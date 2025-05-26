package com.studencki.TimePlan.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActivityDTO {
    private Long subjectId;
    private Long teacherId;
    private Long classroomId;
    private String startTime;
    private int duration;

    private Long groupId;
}