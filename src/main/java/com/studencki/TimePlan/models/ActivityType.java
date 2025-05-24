package com.studencki.TimePlan.models;


public enum ActivityType {
    LESSON(0),
    LECTURE(1);

    private final int value;

    ActivityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}