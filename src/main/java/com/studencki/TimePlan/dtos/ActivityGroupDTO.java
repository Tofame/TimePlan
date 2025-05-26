package com.studencki.TimePlan.dtos;

import com.studencki.TimePlan.models.ActivityType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActivityGroupDTO {
    private ActivityType type;
    private int groupNumber;
}