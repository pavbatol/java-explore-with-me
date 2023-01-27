package ru.practicum.ewm.stats.model;

import lombok.Value;

@Value
public class StatsDtoResponse {
    String app;
    String uri;
    Long hits;
}
