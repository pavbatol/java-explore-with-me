package ru.practicum.ewm.stats.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.model.StatsDtoRequest;
import ru.practicum.ewm.stats.model.StatsDtoResponse;

import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {
    @Override
    public void add(StatsDtoRequest dto) {

    }

    @Override
    public List<StatsDtoResponse> get(String start, String end, List<String> uris, Boolean unique) {
        return null;
    }
}
