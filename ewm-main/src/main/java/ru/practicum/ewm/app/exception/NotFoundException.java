package ru.practicum.ewm.app.exception;

public class NotFoundException extends AbstractBaseException {

    public static final String REASON = "The required object was not found.";

    public NotFoundException(String reason, String message) {
        super(reason, message);
    }

    public NotFoundException(String message) {
        this(REASON, message);
    }
}
