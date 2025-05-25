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

    public static ActivityType fromValue(int value) {
        for (ActivityType type : ActivityType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ActivityType value: " + value);
    }
}