package ru.practicum.stats.model;

import lombok.Value;

@Value
public class StatsDtoResponse {
    String app;
    String uri;
    Long hits;
}
