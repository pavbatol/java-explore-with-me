package ru.practicum.ewm.stats.model;

import org.springframework.stereotype.Component;

@Component
public class StatsMapper {
    public Stats toEntity(StatsDtoRequest dto) {
        return new Stats()
                .setId(dto.getId())
                .setApp(dto.getApp())
                .setUri(dto.getUri())
                .setIp(dto.getIp())
                .setTimestamp(dto.getTimestamp());
    }

    public StatsDtoResponse dtoResponse(Stats stats, Long hits) {
        return new StatsDtoResponse(
                stats.getApp(),
                stats.getUri(),
                hits
        );
    }
}
