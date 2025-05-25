package com.studencki.TimePlan.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActivityDTO {
    private Long subject_id;
    private Long teacher_id;
    private Long classroom_id;
    private String start_time;
    private int duration;
    private int type;
    private Integer group_number;
}