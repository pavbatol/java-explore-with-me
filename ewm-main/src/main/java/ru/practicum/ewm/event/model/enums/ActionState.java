package ru.practicum.ewm.event.model.enums;

public enum ActionState {
    SEND_TO_REVIEW,
    CANCEL_REVIEW;

    public static ActionState by(String stateName) {
        try {
            return ActionState.valueOf(stateName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
