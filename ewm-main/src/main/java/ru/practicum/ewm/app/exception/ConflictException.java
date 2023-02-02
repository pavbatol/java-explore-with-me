package ru.practicum.ewm.app.exception;

public class ConflictException extends AbstractBaseException {

    public static final String REASON = "For the requested operation the conditions are not met.";

    public ConflictException(String reason, String message) {
        super(reason, message);
    }

    public ConflictException(String message) {
        this(REASON, message);
    }
}
