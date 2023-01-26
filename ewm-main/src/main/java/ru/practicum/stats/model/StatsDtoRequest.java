package ru.practicum.stats.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Value
public class StatsDtoRequest {

    @NotBlank
    String app;

    @NotBlank
    String uri;

    @NotBlank
    String ip;

    LocalDateTime timestamp;
}
