package ru.practicum.ewm.common.exception.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

@Value
public class ErrorResponse {
    @JsonProperty("offset-timestamp")
    OffsetDateTime timestamp = OffsetDateTime.now();

    @JsonProperty("end-point")
    String endPoint;

    @JsonProperty("status")
    int status;

    @JsonProperty("error")
    String error;

    @JsonProperty("reasons")
    List<String> reasons;
}
