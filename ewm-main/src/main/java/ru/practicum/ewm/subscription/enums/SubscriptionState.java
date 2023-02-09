package ru.practicum.ewm.subscription.enums;

public enum SubscriptionState {
    PENDING,
    CONFIRMED,
    REJECTED;

    public static SubscriptionState by(String stateName) {
        try {
            return SubscriptionState.valueOf(stateName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }
}
