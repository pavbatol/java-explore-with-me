package ru.practicum.ewm.exeption.handler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PersistenceException.class})
    protected ResponseEntity<Object> handlePersistenceEx(PersistenceException ex, WebRequest request) {
        String message = "Failed attempt to save";
        return makeResponseEntity(message, ex, BAD_REQUEST, request);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        String message = "Unreadable JSON";
        return makeResponseEntity(message, ex, status, request);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        String message = "Incorrect data";
        return makeResponseEntity(message, ex, status, request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleThrowableEx(Throwable ex, WebRequest request) {
        String message = "Unexpected error";
        return makeResponseEntity(message, ex, INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> makeResponseEntity(String message,
                                                      Throwable ex,
                                                      HttpStatus status,
                                                      WebRequest request) {
        log.error(message + ": {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = makeBody(message, status, request, ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    private ErrorResponse makeBody(String message, HttpStatus status, WebRequest request, Throwable ex) {
        List<String> reasons;
        if (ex instanceof BindException) {
            reasons = ((BindException) ex)
                    .getAllErrors()
                    .stream()
                    .map(this::getErrorString)
                    .collect(Collectors.toList());
        } else {
            reasons = Arrays.stream(ex.getMessage().split(", ")).collect(Collectors.toList());
        }
        return new ErrorResponse(
                getRequestURI(request),
                status.value(),
                message,
                reasons);
    }

    private String getErrorString(ObjectError error) {
        if (error instanceof FieldError) {
            return ((FieldError) error).getField() + ' ' + error.getDefaultMessage();
        }
        return error.getDefaultMessage();
    }

    private String getRequestURI(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            HttpServletRequest requestHttp = ((ServletWebRequest) request).getRequest();
            return String.format("%s %s", requestHttp.getMethod(), requestHttp.getRequestURI());
        } else {
            return "";
        }
    }
}
