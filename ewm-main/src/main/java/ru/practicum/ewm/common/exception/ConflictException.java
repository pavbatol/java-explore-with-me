package ru.practicum.ewm.common.exception;

public class ConflictException extends AbstractBaseException {
    public ConflictException(String reason, String message) {
        super(reason, message);
    }

    public ConflictException(String message) {
        this("For the requested operation the conditions are not met.", message);
    }
}
