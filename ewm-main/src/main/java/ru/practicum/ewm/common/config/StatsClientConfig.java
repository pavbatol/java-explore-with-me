package ru.practicum.ewm.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.stats.client.StatsClient;

@Configuration
@RequiredArgsConstructor
public class StatsClientConfig {

    @Value("${app.stats-server-url}")
    private String serverUrl;

    @Value("${app.name}")
    private String app;

    @Value("${app.format.date-time}")
    private String format;

    private final RestTemplateBuilder builder;

    @Bean
    public StatsClient makeStatsClient() {
        return new StatsClient(
                serverUrl,
                app,
                format,
                builder
        );
    }
}
