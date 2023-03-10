package ru.practicum.ewm.app.exception;

import javax.validation.ConstraintDeclarationException;

public class ConflictException extends ConstraintDeclarationException {

    private static final String DEFAULT_REASON = "For the requested operation the conditions are not met.";
    public final String reason;

    public ConflictException(String reason, String message) {
        super(message);
        this.reason = reason;
    }

    public ConflictException(String message) {
        this(DEFAULT_REASON, message);
    }

    public String getReason() {
        return reason;
    }
}
