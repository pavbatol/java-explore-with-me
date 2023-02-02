package ru.practicum.ewm.app.exception.handler;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class ErrorResponse {

    LocalDateTime timestamp = LocalDateTime.now();

    String mapping;

    String status;

    String reason;

    String message;

    String details;

    List<String> errors;
    List<String> trace;
}
