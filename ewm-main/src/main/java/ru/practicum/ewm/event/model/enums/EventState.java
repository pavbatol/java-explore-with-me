package ru.practicum.ewm.event.model.enums;

public enum EventState {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static EventState by(String stateName) {
        try {
            return EventState.valueOf(stateName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
