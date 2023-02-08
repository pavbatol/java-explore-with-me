package ru.practicum.stats.server.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.StatsDtoRequest;
import ru.practicum.stats.dto.StatsDtoResponse;
import ru.practicum.stats.server.stats.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;
    @Value("${app.format.date-time}")
    private String format;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody StatsDtoRequest dto) {
        log.debug("POST (add) with dto={},", dto);
        statsService.add(dto);
    }

    @GetMapping("/stats")
    public List<StatsDtoResponse> get(@RequestParam("start") String start,
                                      @RequestParam("end") String end,
                                      @RequestParam(value = "uris", required = false) List<String> uris,
                                      @RequestParam(value = "unique", defaultValue = "false") Boolean unique) {
        log.debug("GET (get) with start={}, end={}, uris={}, unique={},", start, end, uris, unique);
        return statsService.find(toLocalDateTime(start), toLocalDateTime(end), uris, unique);
    }

    private LocalDateTime toLocalDateTime(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(format));
    }
}
