package ru.practicum.stats.server.stats.service;




import ru.practicum.stats.dto.StatsDtoRequest;
import ru.practicum.stats.dto.StatsDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void add(StatsDtoRequest dto);

    List<StatsDtoResponse> find(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
