package ru.practicum.ewm.app.exception;

import lombok.Getter;

public abstract class AbstractBaseException extends RuntimeException {

    @Getter
    private final String reason;

    public AbstractBaseException(String reason, String message) {
        super(message);
        this.reason = reason;
    }
}
