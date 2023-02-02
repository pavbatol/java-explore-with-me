package ru.practicum.ewm.event.model.enums;

public enum State {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static State by(String stateName) {
        try {
            return State.valueOf(stateName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
