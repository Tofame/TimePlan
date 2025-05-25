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
@Table(name = "Activity_Group")
public class ActivityGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private ActivityType type;
    @Column(name = "group_number")
    private int groupNumber;
}