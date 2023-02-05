package ru.practicum.ewm.request.model.enums;

public enum RequestState {
    PENDING,
    CONFIRMED,
    REJECTED;

    public static RequestState by(String stateName) {
        try {
            return RequestState.valueOf(stateName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
