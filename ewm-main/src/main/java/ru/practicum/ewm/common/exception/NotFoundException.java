package ru.practicum.ewm.common.exception;

public class NotFoundException extends AbstractBaseException {
    public NotFoundException(String reason, String message) {
        super(reason, message);
    }

    public NotFoundException(String message) {
        this("The required object was not found.", message);
    }
}
