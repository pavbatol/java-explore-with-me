package ru.practicum.ewm.client;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class StatsDtoRequest {
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp;
}
