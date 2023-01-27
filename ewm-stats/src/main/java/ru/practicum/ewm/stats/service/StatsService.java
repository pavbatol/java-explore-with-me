package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.stats.model.StatsDtoRequest;
import ru.practicum.ewm.stats.model.StatsDtoResponse;

import java.util.List;

public interface StatsService {
    void add(StatsDtoRequest dto);

    List<StatsDtoResponse> get(String start, String end, List<String> uris, Boolean unique);
}
